package com.jericho2code.app_finance_manager.screens.transaction_list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.jericho2code.app_finance_manager.model.repositories.AccountRepository
import com.jericho2code.app_finance_manager.model.repositories.TransactionRepository
import javax.inject.Inject

class TransactionListViewModelFactory @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TransactionListViewModel::class.java)) {
            TransactionListViewModel(transactionRepository, accountRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}