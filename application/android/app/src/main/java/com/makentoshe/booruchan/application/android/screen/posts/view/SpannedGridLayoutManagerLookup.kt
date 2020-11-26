package com.makentoshe.booruchan.application.android.screen.posts.view

import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import com.makentoshe.booruchan.application.android.screen.posts.model.PostsPagedAdapter
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.deserialize.PostDeserialize

// If htwRatio more than border - there is a vertical image, so it should place in 2 spans vertically
private const val VERTICAL_RATIO_BORDER = 1.5

// If htwRatio less than border - there is a horizontal image, so it should place in 2 spans horizontally
private const val HORIZONTAL_RATIO_BORDER = 0.5

/** Performs grid cell dimensions calculation */
internal class SpannedGridLayoutManagerLookup(
    private val postsAdapter: PostsPagedAdapter
) : SpannedGridLayoutManager.SpanSizeLookup({ position ->
    if (postsAdapter.currentList?.size == position) {
        buildFooterSpan()
    } else {
        buildItemSpan(postsAdapter.currentList?.get(position))
    }
})

/** [SpanSize] for footer */
private fun buildFooterSpan() = SpanSize(3, 1)

/** [SpanSize] for default spans */
private fun buildErrorSpan() = SpanSize(1, 1)

private fun buildItemSpan(result: Result<PostDeserialize<Post>>?): SpanSize {
    val post = getPostFromResult(result)?.post
    return when {
        post?.htwRatio ?: Float.MIN_VALUE > VERTICAL_RATIO_BORDER -> SpanSize(1, 2)
        post?.htwRatio ?: Float.MAX_VALUE < HORIZONTAL_RATIO_BORDER -> SpanSize(2, 1)
        else -> buildErrorSpan()
    }
}

@Suppress("IfThenToSafeAccess") //Main cause is the unstable Result class
private fun getPostFromResult(result: Result<PostDeserialize<Post>>?): PostDeserialize<Post>? {
    return if (result == null) null else result.getOrNull()
}
