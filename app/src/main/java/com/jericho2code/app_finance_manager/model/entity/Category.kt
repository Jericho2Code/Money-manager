package com.jericho2code.app_finance_manager.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.model.entity.Category.Companion.CATEGORY_TABLE

@Entity(tableName = CATEGORY_TABLE)
class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var title: String = "",
    var description: String = "",
    var baseTransactionType: TransactionType = TransactionType.SPENDING_TRANSACTION,
    var color: Int = R.color.base_category_background,
    var iconId: Int = R.drawable.ic_money
) {
    companion object {
        const val CATEGORY_TABLE = "category_table"
        const val ID = "id"
    }
}