package com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.view

import androidx.paging.PagingDataAdapter
import com.makentoshe.booruchan.screen.boorucontent.domain.BooruPreviewPostUi

// If htwRatio more than border - there is a vertical image, so it should place in 2 spans vertically
private const val VERTICAL_RATIO_BORDER = 1.5

// If htwRatio less than border - there is a horizontal image, so it should place in 2 spans horizontally
private const val HORIZONTAL_RATIO_BORDER = 0.5

/** Performs grid cell dimensions calculation */
internal class SpannedGridLayoutManagerLookup(
    private val adapter: PagingDataAdapter<BooruPreviewPostUi, *>,
) : SpannedGridLayoutManager.SpanSizeLookup({ position ->
    if (adapter.itemCount == position) {
        buildFooterSpan()
    } else {
        buildItemSpan(adapter.peek(position))
    }
})

//
/** [SpanSize] for footer */
private fun buildFooterSpan() = SpanSize(3, 1)

/** [SpanSize] for default spans */
private fun buildErrorSpan() = SpanSize(1, 1)

private fun buildItemSpan(booruPreviewPostUi: BooruPreviewPostUi?) = when {
    (booruPreviewPostUi?.hwRatio ?: Float.MIN_VALUE) > VERTICAL_RATIO_BORDER -> {
        SpanSize(1, 2)
    }
    (booruPreviewPostUi?.hwRatio ?: Float.MAX_VALUE) < HORIZONTAL_RATIO_BORDER -> {
        SpanSize(2, 1)
    }
    else -> buildErrorSpan()
}