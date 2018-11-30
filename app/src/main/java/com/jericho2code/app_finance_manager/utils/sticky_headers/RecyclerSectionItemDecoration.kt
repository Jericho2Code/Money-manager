package ru.kinoplan24.app.presentation.utils.sticky_headers

import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *
 * Реализация sticky header'ов (самый верхний хеадер остаётся в начале списка) на основе item decoration
 *
 */
class RecyclerSectionItemDecoration<T>(
    private val stickyHeaderCallback: StickyHeaderCallback<T>,
    private val headerLayoutId: Int,
    private val sticky: Boolean = false
) : RecyclerView.ItemDecoration() {

    private var headerView: View? = null

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if (headerView == null) {
            headerView = headerView(parent)
        }

        if (stickyHeaderCallback.hasHeader(position)) {
            outRect.top = headerView?.layoutParams?.height ?: 0
        }
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        headerView?.let { headerView ->
            fixLayoutSize(headerView, parent)
            var previousHeader: T? = null
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)
                val position = parent.getChildAdapterPosition(child)
                val header = stickyHeaderCallback.header(position)

                if (header != null && header != previousHeader) {
                    stickyHeaderCallback.bindHeaderView(headerView, header)
                    drawHeader(canvas, child, headerView)
                    previousHeader = header
                }
            }
        }
    }

    private fun headerView(parent: ViewGroup) =
        LayoutInflater.from(parent.context).inflate(headerLayoutId, parent, false)

    private fun drawHeader(c: Canvas, child: View, headerView: View) {
        c.save()
        if (sticky) {
            c.translate(0F, Math.max(0, child.top - headerView.height).toFloat())
        } else {
            c.translate(0F, (child.top - headerView.height).toFloat())
        }
        headerView.draw(c)
        c.restore()
    }

    private fun fixLayoutSize(view: View?, parent: ViewGroup) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)
        val childWidth = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingLeft + parent.paddingRight,
            view?.layoutParams!!.width
        )
        val childHeight = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )
        view.measure(childWidth, childHeight)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

}
