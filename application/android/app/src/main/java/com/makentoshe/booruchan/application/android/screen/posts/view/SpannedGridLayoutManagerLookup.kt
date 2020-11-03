package com.makentoshe.booruchan.application.android.screen.posts.view

import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager

/** Performs grid cell dimensions calculation */
internal class SpannedGridLayoutManagerLookup : SpannedGridLayoutManager.SpanSizeLookup({ position ->
    if (position == 0) SpanSize(2, 2) else SpanSize(1, 1)
})