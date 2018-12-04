package com.jericho2code.app_finance_manager.screens.transaction_list

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.extensions.color
import com.jericho2code.app_finance_manager.application.extensions.drawable
import com.jericho2code.app_finance_manager.application.extensions.drawableIdByName
import com.jericho2code.app_finance_manager.application.extensions.toFullDateString
import com.jericho2code.app_finance_manager.model.entity.TransactionType
import com.jericho2code.app_finance_manager.model.entity.TransactionWithCategory
import ru.kinoplan24.app.presentation.utils.adapters.SimpleListAdapter

class TransactionAdapter : SimpleListAdapter<TransactionListItem, RecyclerView.ViewHolder>({ viewType ->
    when (viewType) {
        REGULAR_ITEM -> R.layout.list_item_transaction
        HEADER_ITEM -> R.layout.list_item_transaction_header_day_summary
        else -> R.layout.list_item_transaction
    }
}) {

    companion object {
        const val REGULAR_ITEM = 0
        const val HEADER_ITEM = 1
    }

    var onItemClickListener: ((template: TransactionWithCategory) -> Unit)? = null

    override fun onCreateViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        REGULAR_ITEM -> RegularItemHolder(view)
        HEADER_ITEM -> HeaderItemHolder(view)
        else -> RegularItemHolder(view)
    }


    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is TransactionRegularListItem -> REGULAR_ITEM
        is TransactionHeaderListItem -> HEADER_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: TransactionListItem, position: Int) {
        when (holder) {
            is RegularItemHolder -> holder.bind(item as TransactionRegularListItem, onItemClickListener)
            is HeaderItemHolder -> holder.bind(item as TransactionHeaderListItem)
        }
    }

    class HeaderItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateView = itemView.findViewById<TextView>(R.id.transaction_header_date_text)
        private val deltaView = itemView.findViewById<TextView>(R.id.transaction_header_delta_text)

        fun bind(item: TransactionHeaderListItem) {
            dateView.text = item.date.toFullDateString()
            val sing = if (item.dayDelta > 0) "+" else ""
            deltaView.text = sing + item.dayDelta.toString()
            deltaView.setTextColor(
                itemView.context.color(
                    when {
                        item.dayDelta < 0 -> R.color.spending_grey
                        item.dayDelta > 0 -> R.color.profit
                        else -> R.color.list_item_secondary
                    }
                )
            )
        }
    }

    class RegularItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.transaction_item_title_text)
        private val categoryTitle = itemView.findViewById<TextView>(R.id.transaction_item_category_text)
        private val value = itemView.findViewById<TextView>(R.id.transaction_item_value_text)
        private val imageBackground = itemView.findViewById<View>(R.id.transaction_item_image_background)
        private val categoryIcon = itemView.findViewById<ImageView>(R.id.transaction_item_image_category)

        @SuppressLint("ResourceAsColor")
        fun bind(
            item: TransactionRegularListItem,
            onItemClickListener: ((template: TransactionWithCategory) -> Unit)?
        ) {
            val context = itemView.context
            val transaction = item.transaction.transaction!!
            val category = item.transaction.category.firstOrNull()

            onItemClickListener?.let { onClick ->
                itemView.setOnClickListener {
                    onClick(item.transaction)
                }
            }

            title.text = transaction.title
            val sign = when (transaction.transactionType) {
                TransactionType.SPENDING_TRANSACTION -> "-"
                TransactionType.PROFIT_TRANSACTION -> "+"
                else -> ""
            }

            category?.let { category ->
                categoryTitle.text = category.title
                imageBackground.background = GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    setColor(category.backgroundColor)
                }

                categoryIcon.setImageDrawable(context.drawable(category.iconIdName?.let {
                    context.drawableIdByName(it)
                } ?: R.drawable.ic_money, context?.color(R.color.icon_white)!!))
            }

            value.text = sign + transaction.value.toString()
            value.setTextColor(
                context.color(
                    when (transaction.transactionType) {
                        TransactionType.SPENDING_TRANSACTION -> R.color.spending_grey
                        TransactionType.PROFIT_TRANSACTION -> R.color.profit
                        TransactionType.TRANSFER_TRANSACTION -> R.color.transfer
                    }
                )
            )
        }
    }
}