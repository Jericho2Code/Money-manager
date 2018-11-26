package com.jericho2code.app_finance_manager.screens.add_edit_transaction

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
import com.jericho2code.app_finance_manager.application.extensions.hideKeyboard
import com.jericho2code.app_finance_manager.application.extensions.showToast
import com.jericho2code.app_finance_manager.model.entity.Transaction
import com.jericho2code.app_finance_manager.model.entity.TransactionType
import kotlinx.android.synthetic.main.fragment_add_edit_transaction.*
import kotlinx.android.synthetic.main.view_toolbar.*
import org.threeten.bp.LocalDate

class AddEditTransactionFragment : Fragment() {

    lateinit var viewModel: AddEditTransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(AddEditTransactionViewModel::class.java)
        (activity?.application  as? ApplicationComponentOwner)
            ?.applicationComponent()
            ?.plusTransactionAddEditComponent()
            ?.inject(viewModel)
        viewModel.categoryLiveData.observe(this, Observer { category ->
            transition_category_input.setText(category?.title ?: "")
            category?.let {
                when (it.baseTransactionType) {
                    TransactionType.SPENDING_TRANSACTION -> spending.isChecked = true
                        TransactionType.PROFIT_TRANSACTION -> profit.isChecked = true
                    else -> spending.isChecked = true
                }
            } ?: run {
                spending.isChecked = true
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_edit_transaction, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationIcon(R.drawable.ic_close)
        toolbar.setTitle(R.string.add_transaction)
        toolbar.inflateMenu(R.menu.save_transaction)
        toolbar.setNavigationOnClickListener {
            context?.hideKeyboard(this.view!!)
            findNavController().navigateUp()
        }
        toolbar.menu.findItem(R.id.save_changes).setOnMenuItemClickListener {
            context?.hideKeyboard(this.view!!)
            viewModel.saveTransaction(
                Transaction(
                    value = transition_sum_input.text.toString().toDoubleOrNull() ?: 0.0,
                    title = transition_title_input.text.toString(),
                    description = transition_description_input.text.toString(),
                    date = LocalDate.now(),
                    transactionType = when (operation_type_group.checkedChipId) {
                        R.id.spending -> TransactionType.SPENDING_TRANSACTION
                        R.id.profit -> TransactionType.PROFIT_TRANSACTION
                        else -> TransactionType.SPENDING_TRANSACTION
                    },
                    categoryId = viewModel.categoryLiveData.value?.id ?: 0
                )
            ).subscribe(
                {
                    context?.showToast(R.string.transaction_saved)
                    findNavController().navigateUp()
                },
                {
                    Snackbar.make(view, it.localizedMessage, Snackbar.LENGTH_SHORT).show()
                }
            )
            true
        }
        transition_category_input.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_addEditTransactionFragment_to_selectCategoryFragment)
        )
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.setCategory(null)
    }

    override fun onPause() {
        super.onPause()
        context?.hideKeyboard(view!!)
    }
}