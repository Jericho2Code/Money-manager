package com.jericho2code.app_finance_manager.model.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.jericho2code.app_finance_manager.model.entity.Transaction
import com.jericho2code.app_finance_manager.model.entity.Transaction.Companion.ID
import com.jericho2code.app_finance_manager.model.entity.Transaction.Companion.TRANSACTION_TABLE
import com.jericho2code.app_finance_manager.model.entity.TransactionWithCategory
import io.reactivex.Single

@Dao
interface TransactionDao : BaseDao<Transaction> {
    @Query("SELECT * FROM $TRANSACTION_TABLE")
    fun transactions(): LiveData<List<Transaction>>

    @android.arch.persistence.room.Transaction
    @Query("SELECT * FROM $TRANSACTION_TABLE")
    fun transactionsWithCategory(): LiveData<List<TransactionWithCategory>>

    @Query("SELECT * FROM $TRANSACTION_TABLE WHERE $ID = :id")
    fun transactionById(id: Long): Single<Transaction>
}