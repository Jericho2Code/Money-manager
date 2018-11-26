package ru.kinoplan24.app.presentation.utils.adapters

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseSimpleAdapter<VH : RecyclerView.ViewHolder>(val layout: (viewType: Int) -> Int) : RecyclerView.Adapter<VH>() {
    constructor(@LayoutRes layout: Int) : this({ layout })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            onCreateViewHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(
                                    layout(viewType),
                                    parent,
                                    false),
                    viewType
            )

    /** The same as [onCreateViewHolder], but with already inflated
     * view as argument. */
    abstract fun onCreateViewHolder(view: View, viewType: Int): VH
}