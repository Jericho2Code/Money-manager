package com.jericho2code.app_finance_manager.screens.transaction_list

import android.arch.lifecycle.ViewModel
import com.jericho2code.app_finance_manager.model.repositories.TransactionRepository
import javax.inject.Inject

class TransactionListViewModel : ViewModel() {

    @Inject
    lateinit var transactionRepository: TransactionRepository

    fun transactionsWithCategory() = transactionRepository.transactionsWithCategories()
}