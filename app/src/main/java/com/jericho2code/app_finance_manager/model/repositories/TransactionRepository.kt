package com.jericho2code.app_finance_manager.model.repositories

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

    fun transactions(): Single<List<Transaction>> = transactionDao.transactions()
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)

    fun transactionsWithCategories(): Single<List<TransactionWithCategory>> = transactionDao.transactionsWithCategory()
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)

    fun saveTransaction(transaction: Transaction): Single<Unit> = Single.fromCallable { transactionDao.insert(transaction) }
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)

}