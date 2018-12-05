package com.jericho2code.app_finance_manager.model.repositories

import android.arch.lifecycle.LiveData
import com.jericho2code.app_finance_manager.model.database.dao.AccountDao
import com.jericho2code.app_finance_manager.model.entity.Account
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val accountDao: AccountDao,
    private val uiScheduler: Scheduler,
    private val ioScheduler: Scheduler
) {

    fun accounts(): LiveData<List<Account>> = accountDao.accounts()

    fun addAccount(account: Account) = Single.fromCallable { accountDao.insert(account) }
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)

    fun updateAccount(account: Account) = Single.fromCallable { accountDao.update(account) }
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
}