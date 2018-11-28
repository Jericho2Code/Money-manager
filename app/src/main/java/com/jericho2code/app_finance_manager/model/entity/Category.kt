package com.jericho2code.app_finance_manager.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.content.Context
import android.os.Parcelable
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.extensions.color
import com.jericho2code.app_finance_manager.application.extensions.nameForId
import com.jericho2code.app_finance_manager.application.extensions.str
import com.jericho2code.app_finance_manager.model.entity.Category.Companion.CATEGORY_TABLE
import kotlinx.android.parcel.Parcelize

@Entity(tableName = CATEGORY_TABLE)
@Parcelize
class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var title: String = "",
    var baseTransactionType: TransactionType = TransactionType.SPENDING_TRANSACTION,
    var backgroundColor: Int = R.color.base_category_background,
    var iconIdName: String? = null
): Parcelable {
    companion object {
        const val CATEGORY_TABLE = "category_table"
        const val ID = "id"

        fun baseCategories(context: Context): List<Category> {
            return listOf(
                Category(
                    title = context.str(R.string.category_title_food),
                    iconIdName = context.nameForId(R.drawable.ic_food),
                    backgroundColor = context.color(R.color.base_category_food),
                    baseTransactionType = TransactionType.SPENDING_TRANSACTION
                ),
                Category(
                    title = context.str(R.string.category_title_passage),
                    iconIdName = context.nameForId(R.drawable.ic_bus),
                    backgroundColor = context.color(R.color.base_category_passage),
                    baseTransactionType = TransactionType.SPENDING_TRANSACTION

                ),
                Category(
                    title = context.str(R.string.cinema_and_theaters),
                    iconIdName = context.nameForId(R.drawable.ic_theaters),
                    backgroundColor = context.color(R.color.base_category_cinema_and_theaters),
                    baseTransactionType = TransactionType.SPENDING_TRANSACTION
                ),
                Category(
                    title = context.str(R.string.home_rent),
                    iconIdName = context.nameForId(R.drawable.ic_home_rent),
                    backgroundColor = context.color(R.color.base_category_rent),
                    baseTransactionType = TransactionType.SPENDING_TRANSACTION
                ),
                Category(
                    title = context.str(R.string.books),
                    iconIdName = context.nameForId(R.drawable.ic_books),
                    backgroundColor = context.color(R.color.base_category_books),
                    baseTransactionType = TransactionType.SPENDING_TRANSACTION
                )
                // clothes
                // soft
            )
        }
    }
}