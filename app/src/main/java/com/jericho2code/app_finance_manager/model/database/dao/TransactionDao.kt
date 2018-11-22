package com.jericho2code.app_finance_manager.model.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.jericho2code.app_finance_manager.model.entity.Transaction
import com.jericho2code.app_finance_manager.model.entity.Transaction.Companion.ID
import com.jericho2code.app_finance_manager.model.entity.Transaction.Companion.TRANSACTION_TABLE
import io.reactivex.Single

@Dao
interface TransactionDao : BaseDao<Transaction> {
    @Query("SELECT * FROM $TRANSACTION_TABLE")
    fun transaction(): Single<List<Transaction>>

    @Query("SELECT * FROM $TRANSACTION_TABLE WHERE $ID = :id")
    fun transactionById(id: Long): Single<Transaction>
}