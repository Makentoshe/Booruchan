package com.makentoshe.booruchan.booru.model.gallery

import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.booru.model.gallery.comment.CommentGalleryFactory
import com.makentoshe.booruchan.booru.model.gallery.post.PostOrderedInfinityGalleryFactory
import com.makentoshe.booruchan.booru.view.BooruActivityUI
import java.lang.IllegalArgumentException

interface GalleryFactory {

    fun createGallery(viewModel: BooruViewModel): Gallery

    companion object {

        fun createFactory(@BooruActivityUI.Gallery gallery: Int): GalleryFactory {
            return when (gallery) {
                BooruActivityUI.GALLERY_POST -> PostOrderedInfinityGalleryFactory()
                BooruActivityUI.GALLERY_COMMENT -> CommentGalleryFactory()
                else -> throw IllegalArgumentException(gallery.toString())
            }
        }
    }

}