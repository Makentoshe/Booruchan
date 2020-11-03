package com.makentoshe.booruchan.application.android.screen.posts.view

import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import com.makentoshe.booruchan.application.android.screen.posts.model.PostsPagedAdapter

/** Performs grid cell dimensions calculation */
internal class SpannedGridLayoutManagerLookup(
    private val postsAdapter: PostsPagedAdapter
) : SpannedGridLayoutManager.SpanSizeLookup({ position ->
    val post = postsAdapter.getItem(position).getOrNull()?.post
    if (post == null) {
        // return default span for displaying error while loading/parsing a post
        SpanSize(1,1)
    } else {
        // TODO add span dimension calculations 2x1 or 1x2
        SpanSize(1,1)
    }
})