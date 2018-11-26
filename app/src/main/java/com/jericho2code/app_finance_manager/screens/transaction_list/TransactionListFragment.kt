package com.jericho2code.app_finance_manager.screens.transaction_list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.di.owners.ApplicationComponentOwner
import com.jericho2code.app_finance_manager.model.entity.TransactionType
import com.jericho2code.app_finance_manager.model.entity.TransactionWithCategory
import kotlinx.android.synthetic.main.fragment_transaction_list.*
import kotlinx.android.synthetic.main.view_transaction_list_header.*


class TransactionListFragment : Fragment() {

    private lateinit var viewModel: TransactionListViewModel
    private var adapter = TransactionAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(TransactionListViewModel::class.java)

        (activity?.application  as? ApplicationComponentOwner)
            ?.applicationComponent()
            ?.plusTransactionListComponent()
            ?.inject(viewModel)

        viewModel.transactionsWithCategory().observe(this, Observer<List<TransactionWithCategory>> { transactions ->
            adapter.items = transactions ?: emptyList()
            initStatisticsCards(transactions ?: emptyList())
        })
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
        transition_list.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        transition_list.adapter = adapter
        add_transaction_fab.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_transactionListFragment_to_addEditTransactionFragment2)
        )

        bottom_bar.inflateMenu(R.menu.main_menu)
        bottom_bar.setNavigationOnClickListener {
            val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
            bottomNavDrawerFragment.show(childFragmentManager, bottomNavDrawerFragment.tag)
        }
        initStatisticsCards(adapter.items)
    }
}