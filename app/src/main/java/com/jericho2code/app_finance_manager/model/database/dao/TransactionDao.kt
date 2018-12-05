package com.jericho2code.app_finance_manager.model.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.jericho2code.app_finance_manager.model.entity.Transaction
import com.jericho2code.app_finance_manager.model.entity.Transaction.Companion.ACCOUNT_ID
import com.jericho2code.app_finance_manager.model.entity.Transaction.Companion.TRANSACTION_TABLE
import com.jericho2code.app_finance_manager.model.entity.TransactionWithCategory

@Dao
interface TransactionDao : BaseDao<Transaction> {
    @android.arch.persistence.room.Transaction
    @Query("SELECT * FROM $TRANSACTION_TABLE WHERE $ACCOUNT_ID = :accountId AND is_template == 0")
    fun transactionsWithCategoryForAccount(accountId: Long): LiveData<List<TransactionWithCategory>>

    @android.arch.persistence.room.Transaction
    @Query("SELECT * FROM $TRANSACTION_TABLE WHERE is_template == 0")
    fun transactionsWithCategory(): LiveData<List<TransactionWithCategory>>
}