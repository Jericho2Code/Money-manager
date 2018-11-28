package com.jericho2code.app_finance_manager.screens.template_list

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
import com.jericho2code.app_finance_manager.application.extensions.setTextOrHideIfEmpty
import com.jericho2code.app_finance_manager.model.entity.TemplateFullObject
import com.jericho2code.app_finance_manager.model.entity.TransactionType
import ru.kinoplan24.app.presentation.utils.adapters.SimpleListAdapter

class TemplateAdapter : SimpleListAdapter<TemplateFullObject, TemplateAdapter.Holder>(
    {
        R.layout.list_item_transaction
    }
) {

    var onItemClickListener: ((template: TemplateFullObject) -> Unit)? = null

    override fun onBindViewHolder(holder: Holder, item: TemplateFullObject, position: Int) {
        holder.bind(item, onItemClickListener)
    }

    override fun onCreateViewHolder(view: View, viewType: Int): Holder = Holder(view)

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.transaction_item_title_text)
        private val description = itemView.findViewById<TextView>(R.id.transaction_item_description_text)
        private val value = itemView.findViewById<TextView>(R.id.transaction_item_value_text)
        private val imageBackground = itemView.findViewById<View>(R.id.transaction_item_image_background)
        private val categoryIcon = itemView.findViewById<ImageView>(R.id.transaction_item_image_category)

        @SuppressLint("ResourceAsColor")
        fun bind(item: TemplateFullObject, onItemClickListener: ((template: TemplateFullObject) -> Unit)?) {
            val context = itemView.context
            val transaction = item.transaction.firstOrNull()
            val category = item.category.firstOrNull()

            onItemClickListener?.let { onClick ->
                itemView.setOnClickListener {
                    onClick(item)
                }
            }

            title.text = transaction?.title ?: ""
            description.setTextOrHideIfEmpty(transaction?.description)
            val sign = when (transaction?.transactionType) {
                TransactionType.SPENDING_TRANSACTION -> "-"
                TransactionType.PROFIT_TRANSACTION -> "+"
                else -> ""
            }

            category?.let { category ->

                imageBackground.background = GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    setColor(category.backgroundColor)
                }

                categoryIcon.setImageDrawable(context.drawable(category.iconIdName?.let {
                    context.drawableIdByName(it)
                } ?: R.drawable.ic_money, context?.color(R.color.icon_white)!!))
            }

            value.text = sign + transaction?.value.toString()
            value.setTextColor(
                context.color(
                    when (transaction?.transactionType) {
                        TransactionType.SPENDING_TRANSACTION -> R.color.spending
                        TransactionType.PROFIT_TRANSACTION -> R.color.profit
                        TransactionType.TRANSFER_TRANSACTION -> R.color.transfer
                        else -> R.color.list_item_primary
                    }
                )
            )
        }
    }
}