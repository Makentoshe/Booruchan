package com.makentoshe.booruchan.booru.model.gallery.comment

import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.booru.model.gallery.Gallery
import com.makentoshe.booruchan.booru.model.gallery.GalleryFactory

class CommentGalleryFactory: GalleryFactory {
    override fun createGallery(viewModel: BooruViewModel): Gallery {
        return CommentGallery()
    }
}