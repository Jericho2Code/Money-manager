package com.jericho2code.app_finance_manager.screens.add_edit_transaction

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.di.owners.ApplicationComponentOwner
import com.jericho2code.app_finance_manager.application.extensions.*
import com.jericho2code.app_finance_manager.model.entity.*
import com.jericho2code.app_finance_manager.utils.OpenFullScreenListener
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_add_edit_transaction.*
import kotlinx.android.synthetic.main.view_toolbar.*
import kotlinx.android.synthetic.main.view_transaction_save_as_template_item.*
import org.threeten.bp.LocalDateTime

class AddEditTransactionFragment : Fragment() {

    private lateinit var viewModel: AddEditTransactionViewModel
    private var accountId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountId = arguments?.getLong(CURRENT_ACCOUNT_ID)
        viewModel = ViewModelProviders.of(activity!!).get(AddEditTransactionViewModel::class.java)
        (activity?.application  as? ApplicationComponentOwner)
            ?.applicationComponent()
            ?.plusTransactionAddEditComponent()
            ?.inject(viewModel)
        viewModel.categoryLiveData.observe(this, Observer { category ->
            transition_category_input.setText(category?.title ?: "")
            val isNotTemplate = viewModel.templateDateLiveData.value == null
            val isNotEditTransaction = viewModel.editTransactionLiveData.value == null
            if (isNotTemplate && isNotEditTransaction) {
                category?.let {
                    when (it.baseTransactionType) {
                        TransactionType.SPENDING_TRANSACTION -> spending.isChecked = true
                        TransactionType.PROFIT_TRANSACTION -> profit.isChecked = true
                        else -> spending.isChecked = true
                    }
                } ?: run {
                    spending.isChecked = true
                }
            }
        })
        viewModel.transactionDateLiveData.observe(this, Observer {
            it?.let { setTransactionDateText(it) }
        })
        viewModel.templateDateLiveData.observe(this, Observer { template ->
            val category = template?.category?.firstOrNull()
            val transaction = template?.transaction?.firstOrNull()
            if (category != null && transaction != null) {
                fillScreenField(transaction, category)
                viewModel.setAsTemplateVisibility(false)
            }
        })
        viewModel.saveAsTemplateVisibilityLiveData.observe(this, Observer { isVisible ->
            transaction_save_as_template_field.visibleOrGone(isVisible ?: true)
        })
        viewModel.editTransactionLiveData.observe(this, Observer { transactionFullObject ->
            val category = transactionFullObject?.category?.firstOrNull()
            val transaction = transactionFullObject?.transaction
            if (category != null && transaction != null) {
                fillScreenField(transaction, category)
                viewModel.setTransactionDate(transaction.date ?: LocalDateTime.now())
                toolbar.setTitle(R.string.edit_transaction)
            }
        })
        viewModel.transactionTypeLiveData.observe(this, Observer {
            updateTransactionTypeSelector(it)
        })
        viewModel.setTransactionDate(LocalDateTime.now())
        val template = arguments?.getParcelable<TemplateFullObject>(TEMPLATE)
        template?.let { viewModel.setTemplate(it) }
        val transaction = arguments?.getParcelable<TransactionWithCategory>(TRANSACTION)
        transaction?.let {
            viewModel.setEditTransaction(it)
            viewModel.setTransactionType(it.transaction?.transactionType ?: TransactionType.SPENDING_TRANSACTION)
        }
        viewModel.setAsTemplateVisibility(template == null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_edit_transaction, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? OpenFullScreenListener)?.onScreenOpen()
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setTitle(R.string.add_transaction)
        toolbar.inflateMenu(R.menu.template_menu)
        toolbar.setNavigationOnClickListener {
            context?.hideKeyboard(this.view!!)
            findNavController().navigateUp()
        }

        toolbar.menu.findItem(R.id.templateListFragment).setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_addEditTransactionFragment_to_templateListFragment)
            true
        }

        viewModel.transactionTypeLiveData.value?.let {
            updateTransactionTypeSelector(it)
        }
        operation_type_group.setOnCheckedChangeListener { _, id ->
            viewModel.setTransactionType(
                when (id) {
                    R.id.spending -> TransactionType.SPENDING_TRANSACTION
                    R.id.profit -> TransactionType.PROFIT_TRANSACTION
                    else -> TransactionType.SPENDING_TRANSACTION
                }
            )
        }

        transaction_save_changes_button.setOnClickListener {
            context?.hideKeyboard(this.view!!)
            val transaction = Transaction(
                value = transition_sum_input.text.toString().toDoubleOrNull() ?: 0.0,
                title = transition_title_input.text.toString(),
                date = viewModel.transactionDateLiveData.value ?: LocalDateTime.now(),
                transactionType = when (operation_type_group.checkedChipId) {
                    R.id.spending -> TransactionType.SPENDING_TRANSACTION
                    R.id.profit -> TransactionType.PROFIT_TRANSACTION
                    else -> TransactionType.SPENDING_TRANSACTION
                },
                categoryId = viewModel.categoryLiveData.value?.id ?: 0,
                accountId = accountId ?: 0
            )

            (viewModel.editTransactionLiveData.value?.let {
                viewModel.updateTransaction(transaction.apply {
                    id = viewModel.editTransactionLiveData.value?.transaction?.id
                })
            } ?: run {
                viewModel.saveTransaction(transaction)
            }).flatMap { transitionId ->
                if (transaction_save_as_template_checkbox.isChecked) {
                    viewModel.saveTransaction(
                        transaction.apply {
                            id = null
                            isTemplate = true
                        }
                    )
                } else {
                    Single.just(-1L)
                }
            }.flatMap { transactionId ->
                if (transactionId != -1L) {
                    viewModel.saveTemplate(
                        Template(
                            usageCount = 1,
                            transactionId = transactionId,
                            categoryId = viewModel.categoryLiveData.value?.id ?: 0
                        )
                    )
                } else {
                    Single.just(-1L)
                }
            }.subscribe(
                {
                    context?.showToast(R.string.transaction_saved)
                    findNavController().popBackStack(R.id.transactionListFragment, false)
                },
                {
                    Snackbar.make(view, it.localizedMessage, Snackbar.LENGTH_SHORT).show()
                }
            )
        }

        date_selector_input.setOnClickListener {
            val transactionDate = viewModel.transactionDateLiveData.value ?: LocalDateTime.now()
            val datePickerDialog = TransactionDatePickerDialog.instance(transactionDate.toLocalDate())
            datePickerDialog.show(childFragmentManager, datePickerDialog.tag)
        }
        transition_category_input.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_addEditTransactionFragment_to_selectCategoryFragment)
        )
        viewModel.transactionDateLiveData.value?.let { setTransactionDateText(it) }

        viewModel.editTransactionLiveData.value?.let {
            toolbar.setTitle(R.string.edit_transaction)
        }
        viewModel.setAsTemplateVisibility(viewModel.templateDateLiveData.value == null)
    }

    @SuppressLint("SetTextI18n")
    private fun setTransactionDateText(date: LocalDateTime) {
        val todayText = if (date.isToday()) context?.str(R.string.today) + ", " else ""
        date_selector_input.setText(todayText + date.toFullDateString())
    }

    private fun fillScreenField(transaction: Transaction, category: Category) {
        viewModel.setCategory(category)
        transition_sum_input.setText(transaction.value.toString())
        transition_title_input.setText(transaction.title)
        viewModel.setTransactionType(transaction.transactionType)
    }

    private fun updateTransactionTypeSelector(transactionType: TransactionType?) {
        when (transactionType) {
            TransactionType.SPENDING_TRANSACTION -> spending.isChecked = true
            TransactionType.PROFIT_TRANSACTION -> profit.isChecked = true
            else -> spending.isChecked = true
        }
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.categoryLiveData.value = null
        viewModel.transactionDateLiveData.value = null
        viewModel.templateDateLiveData.value = null
        viewModel.saveAsTemplateVisibilityLiveData.value = null
        viewModel.editTransactionLiveData.value = null
        viewModel.transactionTypeLiveData.value = null
        (activity as? OpenFullScreenListener)?.onScreenClose()
    }

    override fun onPause() {
        super.onPause()
        context?.hideKeyboard(view!!)
    }

    companion object {
        const val TEMPLATE = "template"
        const val TRANSACTION = "transaction"
        const val CURRENT_ACCOUNT_ID = "current_account_id"

        fun createArgs(accountId: Long) = Bundle().apply {
            putLong(CURRENT_ACCOUNT_ID, accountId)
        }

        fun createArgs(accountId: Long, template: TemplateFullObject) = Bundle().apply {
            putParcelable(TEMPLATE, template)
            putLong(CURRENT_ACCOUNT_ID, accountId)
        }

        fun createArgs(accountId: Long, transaction: TransactionWithCategory) = Bundle().apply {
            putParcelable(TRANSACTION, transaction)
            putLong(CURRENT_ACCOUNT_ID, accountId)
        }
    }
}