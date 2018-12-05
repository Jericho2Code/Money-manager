package com.jericho2code.app_finance_manager.screens.transaction_list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class TransactionListFragment : StateFragment<TransactionListViewModel>() {

    private var adapter = TransactionAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateAccounts().observe(this, Observer { accounts ->
            accounts?.let {
                if (viewModel.currentAccountLiveData.value == null) {
                    viewModel.setCurrentAccount(accounts.first())
                }
            }
        })
        viewModel.currentAccountLiveData.observe(this, Observer { account ->
            account?.let {
                viewModel.updateTransactions(it).observe(this, Observer {
                    viewModel.transactionsLiveDate.postValue(it ?: emptyList())
                })
            }
        })
        viewModel.transactionsLiveDate.observe(this, Observer { transactions ->
            if (transactions.isNullOrEmpty()) {
                viewModel.setState(ScreenState.EMPTY)
            } else {
                val listItems =
                    transactions.sortedByDescending { it.transaction?.date }.groupBy { it.transaction?.date?.dayOfYear }
                        .map { (_, transactions) ->
                            val transactionDayDelta = transactions.sumByDouble {
                                val digit =
                                    if (it.transaction?.transactionType == TransactionType.SPENDING_TRANSACTION) -1 else 1
                                (it.transaction?.value ?: 0.0) * digit
                            }
                            val date = transactions.first().transaction?.date!!
                            val transactionType = transactions.first().transaction!!.transactionType
                            val sortedTransactions = transactions.map { TransactionRegularListItem(it) }
                            listOf(
                                TransactionHeaderListItem(
                                    date,
                                    transactionDayDelta,
                                    transactionType
                                )
                            ) + sortedTransactions
                        }.flatten()
                adapter.items = listItems
                initStatisticsCards(transactions)
                viewModel.setState(ScreenState.CONTENT)
            }
        })

        adapter.onItemClickListener = { transaction ->
            findNavController().navigate(
                R.id.action_transactionListFragment_to_addEditTransactionFragment2,
                viewModel.currentAccountLiveData.value?.id?.let { accountId ->
                    AddEditTransactionFragment.createArgs(accountId, transaction)
                }
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

        add_transaction_fab.setOnClickListener {
            findNavController().navigate(R.id.action_transactionListFragment_to_addEditTransactionFragment2,
                viewModel.currentAccountLiveData.value?.id?.let { accountId ->
                    AddEditTransactionFragment.createArgs(accountId)
                })
        }

        initStatisticsCards(adapter.items.filter { it is TransactionRegularListItem }.map { (it as TransactionRegularListItem).transaction })
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
        transition_list.gone()
        transaction_list_empty.gone()
    }

    override fun showContent() {
        transition_list.visible()
        transaction_list_progress.gone()
        transaction_list_empty.gone()
    }

    override fun showError() {}
    override fun showEmpty() {
        transition_list.visible()
        transaction_list_progress.gone()
        transaction_list_empty.visible()
    }
}