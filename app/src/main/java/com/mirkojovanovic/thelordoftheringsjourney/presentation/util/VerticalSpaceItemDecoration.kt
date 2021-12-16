package com.mirkojovanovic.thelordoftheringsjourney.presentation.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceItemDecoration(
    private val inner: Int,
    private val start: Int = 0,
    private val top: Int = 0,
    private val end: Int = start,
    private val bottom: Int = top,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        fun getItemPosition(view: View): Int = parent.getChildAdapterPosition(view)
        fun getItemCount(): Int = parent.adapter!!.itemCount
        fun isItemAt(position: Int): Boolean = getItemPosition(view) == position
        fun isFirstItem(): Boolean = isItemAt(0)
        fun isLastItem(): Boolean = isItemAt(getItemCount() - 1)

        if (top != 0 && isFirstItem()) outRect.top = top
        if (bottom != 0 && isLastItem()) outRect.bottom = bottom
        if (inner != 0 && !isLastItem()) outRect.bottom = inner
        if (start != 0) outRect.left = start
        if (end != 0) outRect.right = end
    }
}
