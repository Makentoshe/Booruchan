package com.makentoshe.booruchan.application.android.screen.posts.view

import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import com.makentoshe.booruchan.application.android.screen.posts.model.PostsPagedAdapter
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.deserialize.PostDeserialize

// If htwRatio more than border - there is a vertical image, so it should place in 2 spans vertically
private const val VERTICAL_RATIO_BORDER = 1.3

// If htwRatio less than border - there is a horizontal image, so it should place in 2 spans horizontally
private const val HORIZONTAL_RATIO_BORDER = 0.7

/** Performs grid cell dimensions calculation */
internal class SpannedGridLayoutManagerLookup(
    private val postsAdapter: PostsPagedAdapter
) : SpannedGridLayoutManager.SpanSizeLookup({ position ->
    if (postsAdapter.currentList?.size == position) {
        // Footer
        SpanSize(3, 1)
    } else {
        val post = getPostFromResult(postsAdapter.currentList?.get(position))?.post
        if (post == null) {
            // return default span for displaying error while loading/parsing a post
            SpanSize(1, 1)
        } else {
            when {
                post.htwRatio > VERTICAL_RATIO_BORDER -> SpanSize(1, 2)
                post.htwRatio < HORIZONTAL_RATIO_BORDER -> SpanSize(2, 1)
                else -> SpanSize(1, 1)
            }
        }
    }
})

@Suppress("IfThenToSafeAccess") //Main cause is the unstable Result class
internal fun getPostFromResult(result: Result<PostDeserialize<Post>>?): PostDeserialize<Post>? {
    return if (result == null) null else result.getOrNull()
}
