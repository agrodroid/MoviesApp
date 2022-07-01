package com.risajtr.moviesapp.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * ItemDecoration class.
 */
class GridItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            this.left = 15
            this.right = 15
            this.bottom = 90
        }
    }
}