package com.makentoshe.booruchan.application.android.screen.posts.view

import com.makentoshe.booruchan.application.android.screen.posts.model.PostsPagedAdapter

internal class SpannedGridLayoutManagerLookup(private val adapter: PostsPagedAdapter) : SpannedGridLayoutManager.GridSpanLookup {
    override fun getSpanInfo(position: Int): SpannedGridLayoutManager.SpanInfo {
//        println("$position - ${adapter.currentList?.get(position)}")
//        return if (position == 0) {
//            SpannedGridLayoutManager.SpanInfo(2, 2)
//        } else {
           return SpannedGridLayoutManager.SpanInfo(1, 1)
//        }
    }
}