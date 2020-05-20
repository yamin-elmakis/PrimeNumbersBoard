package com.yamin.primeboard.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// the space in PX
class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = space
        outRect.bottom = space
        outRect.left = space
        outRect.right = space
    }
}