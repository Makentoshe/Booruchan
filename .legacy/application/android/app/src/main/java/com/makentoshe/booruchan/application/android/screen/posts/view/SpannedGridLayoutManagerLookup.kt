package com.makentoshe.booruchan.application.android.screen.posts.view

import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import com.makentoshe.booruchan.application.android.screen.posts.model.paging.PostAdapter
import com.makentoshe.booruchan.application.android.screen.posts.model.paging.PostEntity
import com.makentoshe.booruchan.core.post.Post

// If htwRatio more than border - there is a vertical image, so it should place in 2 spans vertically
private const val VERTICAL_RATIO_BORDER = 1.5

// If htwRatio less than border - there is a horizontal image, so it should place in 2 spans horizontally
private const val HORIZONTAL_RATIO_BORDER = 0.5

/** Performs grid cell dimensions calculation */
internal class SpannedGridLayoutManagerLookup(
    private val adapter: PostAdapter
) : SpannedGridLayoutManager.SpanSizeLookup({ position ->
    if (adapter.itemCount == position) {
        buildFooterSpan()
    } else {
        // Some android-like shit TODO(Fix same for PostAdapter)
        buildItemSpan(adapter.peek(position))
    }
})

/** [SpanSize] for footer */
private fun buildFooterSpan() = SpanSize(3, 1)

/** [SpanSize] for default spans */
private fun buildErrorSpan() = SpanSize(1, 1)

private fun buildItemSpan(entity: PostEntity?): SpanSize {
    val post = getPostFromResult(entity)
    return when {
        post?.htwRatio ?: Float.MIN_VALUE > VERTICAL_RATIO_BORDER -> SpanSize(1, 2)
        post?.htwRatio ?: Float.MAX_VALUE < HORIZONTAL_RATIO_BORDER -> SpanSize(2, 1)
        else -> buildErrorSpan()
    }
}

private fun getPostFromResult(entity: PostEntity?): Post? = when (entity) {
    is PostEntity.Success -> entity.post
    else -> null
}
