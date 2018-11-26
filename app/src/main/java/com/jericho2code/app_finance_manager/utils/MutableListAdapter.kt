package ru.kinoplan24.app.presentation.utils.adapters

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView


/** Provides the easiest way to display the items of uniform data.
 *
 * @param M the type of data source.
 * @param items the items of data to display.
 * @param layout item layout resource id. */
abstract class MutableListAdapter<M, VH : RecyclerView.ViewHolder>(layout: (viewType: Int) -> Int) : BaseSimpleAdapter<VH>(layout) {

    constructor(list: MutableList<M>, @LayoutRes layout: Int) : this({ layout }) {
        refreshItems(list)
    }

    val items: MutableList<M> = mutableListOf()

    fun refreshItems(newList: List<M>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    fun updateItem(position: Int, newItem: M) {
        items[position] = newItem
        notifyItemChanged(position)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = onBindViewHolder(holder, items[position], position)

    override fun getItemCount() = items.size

    fun getItemViewType(value: M) = getItemViewType(items.indexOf(value))

    /** The same as [onBindViewHolder], but the second argument is the
     * item at given position from [items]. */
    abstract fun onBindViewHolder(holder: VH, item: M, position: Int)
}