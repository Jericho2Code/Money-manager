package com.jericho2code.app_finance_manager.model.repositories

import android.arch.lifecycle.LiveData
import com.jericho2code.app_finance_manager.model.database.dao.TransactionDao
import com.jericho2code.app_finance_manager.model.entity.Transaction
import com.jericho2code.app_finance_manager.model.entity.TransactionWithCategory
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val uiScheduler: Scheduler,
    private val ioScheduler: Scheduler
) {

    fun transactions(): LiveData<List<TransactionWithCategory>> = transactionDao.transactionsWithCategory()

    fun transactionForAccount(accountId: Long): LiveData<List<TransactionWithCategory>> =
        transactionDao.transactionsWithCategoryForAccount(accountId)

    fun saveTransaction(transaction: Transaction): Single<Long> =
        Single.fromCallable { transactionDao.insert(transaction) }
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)

    fun updateTransaction(transaction: Transaction): Single<Unit> =
        Single.fromCallable { transactionDao.update(transaction) }
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)

}