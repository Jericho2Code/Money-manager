package com.jericho2code.app_finance_manager.screens.transaction_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jericho2code.app_finance_manager.R

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): Holder =
        Holder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.list_item_transaction,
                    parent,
                    false
                )
        )

    override fun getItemCount(): Int = 25

    override fun onBindViewHolder(holder: Holder, position: Int) {}

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
}