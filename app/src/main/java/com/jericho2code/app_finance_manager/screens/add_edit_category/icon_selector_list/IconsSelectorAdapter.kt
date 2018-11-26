package com.jericho2code.app_finance_manager.screens.add_edit_category.icon_selector_list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.extensions.drawable
import ru.kinoplan24.app.presentation.utils.adapters.SimpleListAdapter

class IconsSelectorAdapter : SimpleListAdapter<Int, IconsSelectorAdapter.Holder>(
    {
        R.layout.list_item_icon_selector
    }
) {

    var onItemClickListener: ((iconId: Int) -> Unit)? = null

    override fun onBindViewHolder(holder: Holder, item: Int, position: Int) {
        holder.bind(item, onItemClickListener)
    }

    override fun onCreateViewHolder(view: View, viewType: Int): Holder = Holder(view)

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon = itemView.findViewById<ImageView>(R.id.icon)

        fun bind(item: Int, onItemClickListener: ((iconId: Int) -> Unit)?) {
            icon.setImageDrawable(itemView.context.drawable(item))
            onItemClickListener?.let { onItemClick ->
                itemView.setOnClickListener { onItemClick(item) }
            }
        }
    }
}