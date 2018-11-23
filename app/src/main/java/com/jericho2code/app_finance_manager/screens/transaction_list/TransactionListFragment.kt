package com.jericho2code.app_finance_manager.screens.transaction_list

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
import com.jericho2code.app_finance_manager.model.repositories.TransactionRepository
import kotlinx.android.synthetic.main.fragment_transaction_list.*
import javax.inject.Inject


class TransactionListFragment : Fragment() {

    @Inject
    lateinit var transactionRepository: TransactionRepository
    var adapter = TransactionAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application  as? ApplicationComponentOwner)
            ?.applicationComponent()
            ?.plusTransactionListComponent()
            ?.inject(this)
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

        transactionRepository.transactionsWithCategories().subscribe(
            {
                adapter.items = it
            }, {}
        )
    }
}