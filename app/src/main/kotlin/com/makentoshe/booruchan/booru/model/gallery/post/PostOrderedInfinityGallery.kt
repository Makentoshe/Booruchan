package com.makentoshe.booruchan.booru.model.gallery.post

import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.booru.model.gallery.Gallery
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PostOrderedInfinityGallery(private val viewModel: BooruViewModel) : Gallery {

    override fun createView(context: @AnkoViewDslMarker _FrameLayout): View = with(context) {
        swipeRefreshLayout {
            recyclerView {
                id = R.id.booru_content_gallery
                lparams(matchParent, matchParent)
            }
        }.lparams(matchParent, matchParent)
    }
}