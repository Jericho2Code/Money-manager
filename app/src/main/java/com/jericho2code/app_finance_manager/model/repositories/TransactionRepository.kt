package com.jericho2code.app_finance_manager.model.repositories

import android.arch.lifecycle.LiveData
import androidx.annotation.WorkerThread
import com.jericho2code.app_finance_manager.model.database.dao.TransactionDao
import com.jericho2code.app_finance_manager.model.entity.Transaction
import com.jericho2code.app_finance_manager.model.entity.TransactionWithCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val transactionDao: TransactionDao) {

    fun transactions(): LiveData<List<TransactionWithCategory>> = transactionDao.transactionsWithCategory()

    fun transactionForAccount(accountId: Long): LiveData<List<TransactionWithCategory>> =
        transactionDao.transactionsWithCategoryForAccount(accountId)

    @WorkerThread
    suspend fun saveTransaction(transaction: Transaction): Long = withContext(Dispatchers.IO) {
        transactionDao.insert(transaction)
    }

    @WorkerThread
    suspend fun updateTransaction(transaction: Transaction) = withContext(Dispatchers.IO) {
        transactionDao.update(transaction)
    }

}