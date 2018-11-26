package com.jericho2code.app_finance_manager.screens.category_list

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
import com.jericho2code.app_finance_manager.model.entity.Category
import ru.kinoplan24.app.presentation.utils.adapters.SimpleListAdapter

class CategoryAdapter : SimpleListAdapter<Category, CategoryAdapter.Holder>(
    {
        R.layout.list_item_category
    }
) {

    var onItemClickListener: ((category: Category) -> Unit)? = null

    override fun onBindViewHolder(holder: Holder, item: Category, position: Int) {
        holder.bind(item, onItemClickListener)
    }

    override fun onCreateViewHolder(view: View, viewType: Int): Holder = Holder(view)

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.category_item_title_text)
        private val imageBackground = itemView.findViewById<View>(R.id.category_item_image_background)
        private val categoryIcon = itemView.findViewById<ImageView>(R.id.category_item_image)

        @SuppressLint("ResourceAsColor")
        fun bind(item: Category, onItemClickListener: ((category: Category) -> Unit)?) {
            val context = itemView.context
            title.text = item.title
            imageBackground.background = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(item.backgroundColor)
            }
            onItemClickListener?.let { onItemClick ->
                itemView.setOnClickListener { onItemClick(item) }
            }
            categoryIcon.setImageDrawable(context.drawable(item.iconIdName?.let { context.drawableIdByName(it) } ?: R.drawable.ic_money, item.iconColor ?: context?.color(R.color.icon_white)!!))
        }
    }
}