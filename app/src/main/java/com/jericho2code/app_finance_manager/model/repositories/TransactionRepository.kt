package com.jericho2code.app_finance_manager.model.repositories

import com.jericho2code.app_finance_manager.model.database.dao.TransactionDao
import com.jericho2code.app_finance_manager.model.entity.Transaction
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val uiScheduler: Scheduler,
    private val ioScheduler: Scheduler
) {

    fun transactions() = transactionDao.transaction()
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)

    fun saveTransaction(transaction: Transaction) = Single.fromCallable { transactionDao.insert(transaction) }
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)

}