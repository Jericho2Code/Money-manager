package com.jericho2code.app_finance_manager.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.content.Context
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.extensions.color
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
                Category(title = context.str(R.string.category_title_food), iconId = R.drawable.ic_food, color = context.color(R.color.base_category_food)),
                Category(title = context.str(R.string.category_title_passage), iconId = R.drawable.ic_bus, color = context.color(R.color.base_category_passage)),
                Category(title = context.str(R.string.cinema_and_theaters), iconId = R.drawable.ic_theaters, color = context.color(R.color.base_category_cinema_and_theaters)),
                Category(title = context.str(R.string.home_rent), iconId = R.drawable.ic_home_rent, color = context.color(R.color.base_category_rent)),
                Category(title = context.str(R.string.books), iconId = R.drawable.ic_books, color = context.color(R.color.base_category_books))
            // clothes
            // soft
            )
        }
    }
}