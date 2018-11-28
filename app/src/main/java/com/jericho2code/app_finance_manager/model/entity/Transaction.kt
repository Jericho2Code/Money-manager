package com.jericho2code.app_finance_manager.model.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.jericho2code.app_finance_manager.model.entity.Transaction.Companion.TRANSACTION_TABLE
import org.threeten.bp.LocalDateTime

@Entity(tableName = TRANSACTION_TABLE)
class Transaction(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var title: String = "",
    var description: String = "",
    var value: Double = 0.0,
    var date: LocalDateTime? = null,
    var transactionType: TransactionType? = null,
    @ColumnInfo(name = "category_id")
    var categoryId: Long = 2
) {
    companion object {
        const val TRANSACTION_TABLE = "transaction_table"
        const val ID = "id"
    }
}