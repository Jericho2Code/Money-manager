package ru.kinoplan24.app.presentation.utils.sticky_headers

import android.view.View

interface StickyHeaderCallback<T> {
    fun hasHeader(position: Int): Boolean
    fun header(position: Int): T?
    fun bindHeaderView(parentView: View, headerValue: T)
}