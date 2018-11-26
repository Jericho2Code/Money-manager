package ru.kinoplan24.app.presentation.utils.adapters

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView


/** Provides the easiest way to display the items of uniform data.
 *
 * @param M the type of data source.
 * @param items the items of data to display.
 * @param layout item layout resource id. */
abstract class SimpleListAdapter<M, VH : RecyclerView.ViewHolder>(
        layout: (Int) -> Int,
        private val isNotifyOnItemsChange: Boolean = true
) : BaseSimpleAdapter<VH>(layout) {

    constructor(list: List<M>, @LayoutRes layout: Int) : this({ layout }) {
        items = list
    }

    var items: List<M> = emptyList()
        set(value) {
            field = value
            if (isNotifyOnItemsChange) notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: VH, position: Int) = onBindViewHolder(holder, items[position], position)

    override fun getItemCount() = items.size

    fun getItemViewType(value: M) = getItemViewType(items.indexOf(value))

    /** The same as [onBindViewHolder], but the second argument is the
     * item at given position from [items]. */
    abstract fun onBindViewHolder(holder: VH, item: M, position: Int)
}