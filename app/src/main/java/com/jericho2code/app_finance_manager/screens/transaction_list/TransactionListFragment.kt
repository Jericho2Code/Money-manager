package com.jericho2code.app_finance_manager.screens.transaction_list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.di.owners.ApplicationComponentOwner
import com.jericho2code.app_finance_manager.application.extensions.gone
import com.jericho2code.app_finance_manager.application.extensions.visible
import com.jericho2code.app_finance_manager.model.entity.TransactionType
import com.jericho2code.app_finance_manager.model.entity.TransactionWithCategory
import com.jericho2code.app_finance_manager.screens.add_edit_transaction.AddEditTransactionFragment
import com.jericho2code.app_finance_manager.utils.ScreenState
import com.jericho2code.app_finance_manager.utils.StateFragment
import kotlinx.android.synthetic.main.fragment_transaction_list.*
import kotlinx.android.synthetic.main.view_transaction_list_header.*
import ru.kinoplan24.app.presentation.utils.sticky_headers.RecyclerSectionItemDecoration


class TransactionListFragment : StateFragment<TransactionListViewModel>() {

    private var adapter = TransactionAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.transactionsWithCategory().observe(this, Observer<List<TransactionWithCategory>> { transactions ->
            if (transactions?.isNullOrEmpty() == true) {
                viewModel.setState(ScreenState.LOADING)
            } else {
                adapter.items = transactions.sortedByDescending { it.transaction?.date }
                initStatisticsCards(transactions)
                viewModel.setState(ScreenState.CONTENT)
            }
        })

        adapter.onItemClickListener = { transaction ->
            findNavController().navigate(
                R.id.action_transactionListFragment_to_addEditTransactionFragment2,
                AddEditTransactionFragment.createArgs(transaction)
            )
        }
    }

    private fun initStatisticsCards(transactions: List<TransactionWithCategory>) {
        val profit = transactions.filter { it.transaction?.transactionType == TransactionType.PROFIT_TRANSACTION }
            .sumBy { it.transaction?.value?.toInt() ?: 0 }
        val spent = transactions.filter { it.transaction?.transactionType == TransactionType.SPENDING_TRANSACTION }
            .sumBy { it.transaction?.value?.toInt() ?: 0 }
        val total = profit - spent

        total_balance_value.text = "${if (total > 0) "+" else ""}$total \u20BD"
        total_profit_for_last_month_value.text = "+$profit \u20BD"
        total_spent_for_last_month_value.text = "-$spent \u20BD"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_transaction_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transition_list.adapter = adapter
        transition_list.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        transition_list.addItemDecoration(
            RecyclerSectionItemDecoration(
                adapter,
                R.layout.header_item_transaction_day_summary,
                sticky = true
            )
        )
        add_transaction_fab.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_transactionListFragment_to_addEditTransactionFragment2)
        )

        bottom_bar.inflateMenu(R.menu.main_menu)
        bottom_bar.setNavigationOnClickListener {
            val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
            bottomNavDrawerFragment.show(childFragmentManager, bottomNavDrawerFragment.tag)
        }
        bottom_bar.menu.findItem(R.id.templateListFragment).setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_transactionListFragment_to_templateListFragment)
            true
        }
        initStatisticsCards(adapter.items)
    }

    override fun provideViewModel(): TransactionListViewModel {
        val viewModel = ViewModelProviders.of(this).get(TransactionListViewModel::class.java)
        (activity?.application  as? ApplicationComponentOwner)
            ?.applicationComponent()
            ?.plusTransactionListComponent()
            ?.inject(viewModel)
        return viewModel
    }

    override fun showLoading() {
        transaction_list_progress.visible()
        transaction_list_content.gone()
    }

    override fun showContent() {
        transaction_list_content.visible()
        transaction_list_progress.gone()
    }

    override fun showError() {}
    override fun showEmpty() {}
}