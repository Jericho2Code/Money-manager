package com.jericho2code.app_finance_manager.screens.transaction_list

import android.arch.lifecycle.MutableLiveData
import com.jericho2code.app_finance_manager.model.entity.Account
import com.jericho2code.app_finance_manager.model.entity.TransactionWithCategory
import com.jericho2code.app_finance_manager.model.repositories.AccountRepository
import com.jericho2code.app_finance_manager.model.repositories.TransactionRepository
import com.jericho2code.app_finance_manager.utils.StateOwnerViewModel
import javax.inject.Inject

class TransactionListViewModel : StateOwnerViewModel() {

    @Inject
    lateinit var transactionRepository: TransactionRepository
    @Inject
    lateinit var accountRepository: AccountRepository

    var transactionsLiveDate = MutableLiveData<List<TransactionWithCategory>>()
    var accountsLiveData = MutableLiveData<List<Account>>()
    var currentAccountLiveData = MutableLiveData<Account>()

    fun setCurrentAccount(account: Account) {
        currentAccountLiveData.postValue(account)
    }

    fun updateAccounts() = accountRepository.accounts()

    fun updateTransactions(account: Account) = transactionRepository.transactionForAccount(account.id!!)

}