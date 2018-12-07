package com.jericho2code.app_finance_manager.model.repositories

import android.arch.lifecycle.LiveData
import androidx.annotation.WorkerThread
import com.jericho2code.app_finance_manager.model.database.dao.AccountDao
import com.jericho2code.app_finance_manager.model.entity.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepository @Inject constructor(private val accountDao: AccountDao) {

    fun accounts(): LiveData<List<Account>> = accountDao.accounts()

    @WorkerThread
    suspend fun addAccount(account: Account) = withContext(Dispatchers.IO) {
        accountDao.insert(account)
    }

    @WorkerThread
    suspend fun updateAccount(account: Account) = withContext(Dispatchers.IO) {
        accountDao.update(account)
    }
}