package com.makentoshe.booruchan.application.android.screen.posts.view

import androidx.recyclerview.widget.RecyclerView
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager

class CustomSpannedGridLayoutManager(
    orientation: Orientation, spans: Int
) : SpannedGridLayoutManager(orientation, spans) {
    override fun fillAfter(recycler: RecyclerView.Recycler) = try {
        super.fillAfter(recycler)
    } catch (e: ArithmeticException) {
        // ignore divide by zero at
        // com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager.fillAfter(SpannedGridLayoutManager.kt:623)
    }
}