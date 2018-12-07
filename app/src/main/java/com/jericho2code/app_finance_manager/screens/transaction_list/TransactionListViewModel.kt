package com.jericho2code.app_finance_manager.screens.transaction_list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.jericho2code.app_finance_manager.model.entity.Account
import com.jericho2code.app_finance_manager.model.repositories.AccountRepository
import com.jericho2code.app_finance_manager.model.repositories.TransactionRepository
import com.jericho2code.app_finance_manager.utils.StateOwnerViewModel

class TransactionListViewModel constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) : StateOwnerViewModel() {

    val accountsLiveData: LiveData<List<Account>> = accountRepository.accounts()
    val currentAccountLiveData = MutableLiveData<Account>()

    fun setCurrentAccount(account: Account) {
        currentAccountLiveData.postValue(account)
    }

    fun updateTransactions(account: Account) = transactionRepository.transactionForAccount(account.id!!)

}