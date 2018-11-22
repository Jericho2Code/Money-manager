package com.jericho2code.app_finance_manager.screens.transaction_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.extensions.color
import com.jericho2code.app_finance_manager.application.extensions.setTextOrHideIfEmpty
import com.jericho2code.app_finance_manager.model.entity.Transaction
import com.jericho2code.app_finance_manager.model.entity.TransactionType

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.Holder>() {

    var items: List<Transaction> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): Holder =
        Holder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.list_item_transaction,
                    parent,
                    false
                )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.transaction_item_title_text)
        private val description = itemView.findViewById<TextView>(R.id.transaction_item_description_text)
        private val value = itemView.findViewById<TextView>(R.id.transaction_item_value_text)

        fun bind(item: Transaction) {
            title.text = item.title
            description.setTextOrHideIfEmpty(item.description)
            val sign = when (item.transactionType) {
                TransactionType.SPENDING_TRANSACTION -> "-"
                TransactionType.PROFIT_TRANSACTION -> "+"
                else -> ""
            }
            value.text = sign + item.value.toString()
            value.setTextColor(
                itemView.context.color(
                    when (item.transactionType) {
                        TransactionType.SPENDING_TRANSACTION -> R.color.spending
                        TransactionType.PROFIT_TRANSACTION -> R.color.profit
                        else -> R.color.list_item_primary
                    }
                )
            )
        }
    }
}