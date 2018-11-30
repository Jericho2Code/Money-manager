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
import ru.kinoplan24.app.presentation.utils.sticky_headers.StickyHeaderCallback

class TransactionAdapter : SimpleListAdapter<TransactionWithCategory, TransactionAdapter.Holder>({
    R.layout.list_item_transaction
}), StickyHeaderCallback<TransactionHeaderItem> {
    var onItemClickListener: ((template: TransactionWithCategory) -> Unit)? = null

    override fun onCreateViewHolder(view: View, viewType: Int): Holder = Holder(view)

    override fun onBindViewHolder(holder: Holder, item: TransactionWithCategory, position: Int) {
        holder.bind(item, onItemClickListener)
    }

    override fun hasHeader(position: Int): Boolean {
        val currentItem = items[position].transaction ?: return false
        val previousItem = (if (position > 0) items[position - 1].transaction else null) ?: return true
        return currentItem.date?.dayOfYear?.equals(previousItem.date?.dayOfYear)?.not() == true
    }

    override fun header(position: Int): TransactionHeaderItem? {
        return if (hasHeader(position)) {
            val currentItem = items[position].transaction ?: return null
            val transactionDayDelta = items.filter { it.transaction?.date?.dayOfYear == currentItem.date?.dayOfYear }
                .sumByDouble {
                    val digit = if (it.transaction?.transactionType == TransactionType.SPENDING_TRANSACTION) -1 else 1
                    (it.transaction?.value ?: 0.0) * digit
                }
            TransactionHeaderItem(currentItem.date!!, transactionDayDelta, currentItem.transactionType)
        } else {
            null
        }
    }

    @SuppressLint("SetTextI18n")
    override fun bindHeaderView(parentView: View, headerValue: TransactionHeaderItem) {
        val dateView = parentView.findViewById<TextView>(R.id.transaction_header_date_text)
        val deltaView = parentView.findViewById<TextView>(R.id.transaction_header_delta_text)

        dateView.text = headerValue.date.toFullDateString()
        val sing = if (headerValue.dayDelta > 0) "+" else ""
        deltaView.text = sing + headerValue.dayDelta.toString()
        deltaView.setTextColor(
            parentView.context.color(
                when {
                    headerValue.dayDelta < 0 -> R.color.spending
                    headerValue.dayDelta > 0 -> R.color.profit
                    else -> R.color.list_item_secondary
                }
            )
        )
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.transaction_item_title_text)
        private val categoryTitle = itemView.findViewById<TextView>(R.id.transaction_item_category_text)
        private val value = itemView.findViewById<TextView>(R.id.transaction_item_value_text)
        private val imageBackground = itemView.findViewById<View>(R.id.transaction_item_image_background)
        private val categoryIcon = itemView.findViewById<ImageView>(R.id.transaction_item_image_category)

        @SuppressLint("ResourceAsColor")
        fun bind(item: TransactionWithCategory, onItemClickListener: ((template: TransactionWithCategory) -> Unit)?) {
            val context = itemView.context
            val transaction = item.transaction!!
            val category = item.category.firstOrNull()

            onItemClickListener?.let { onClick ->
                itemView.setOnClickListener {
                    onClick(item)
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
                        TransactionType.SPENDING_TRANSACTION -> R.color.spending
                        TransactionType.PROFIT_TRANSACTION -> R.color.profit
                        TransactionType.TRANSFER_TRANSACTION -> R.color.transfer
                    }
                )
            )
        }
    }
}