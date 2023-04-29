package com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.view

import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(private val mSpaceDp: Float) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            mSpaceDp,
            view.resources.displayMetrics
        )

        outRect.left = (px / 2).toInt()
        outRect.right = (px / 2).toInt()
        outRect.bottom = px.toInt()
    }
}