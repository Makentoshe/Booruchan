package com.makentoshe.booruchan.booru.model.gallery.posts

import android.arch.lifecycle.LifecycleOwner
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.gallery.Gallery
import com.makentoshe.booruchan.booru.model.gallery.GalleryViewModel
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PostOrderedInfinityGallery(private val viewModel: PostOrderedInfinityViewModel) : Gallery {

    override fun createView(context: @AnkoViewDslMarker _FrameLayout, galleryViewModel: GalleryViewModel)
            : View = with(context) {
        swipeRefreshLayout {
            recyclerView {
                id = R.id.booru_content_gallery
                adapter = viewModel.getGalleryAdapter()
                layoutManager = LinearLayoutManager(this.context)
                lparams(matchParent, matchParent)

                galleryViewModel.addSearchTermObserver(context.context as LifecycleOwner) {
                    swapAdapter(viewModel.newGalleryAdapter(it), false)
                }
            }
        }.lparams(matchParent, matchParent)
    }
}