package com.jericho2code.app_finance_manager.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.content.Context
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.extensions.str
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

        fun baseCategories(context: Context): List<Category> {
            return listOf(
                Category(title = context.str(R.string.category_title_food), iconId = R.drawable.ic_food),
                Category(title = context.str(R.string.category_title_passage), iconId = R.drawable.ic_bus)

            )
        }
    }
}