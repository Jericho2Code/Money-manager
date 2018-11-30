package com.jericho2code.app_finance_manager.screens.transaction_list

import com.jericho2code.app_finance_manager.model.repositories.TransactionRepository
import com.jericho2code.app_finance_manager.utils.StateOwnerViewModel
import javax.inject.Inject

class TransactionListViewModel : StateOwnerViewModel() {

    @Inject
    lateinit var transactionRepository: TransactionRepository

    fun transactionsWithCategory() = transactionRepository.transactionsWithCategories()
}