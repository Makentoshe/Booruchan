package com.makentoshe.booruchan.booru.model.gallery.post

import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.booru.model.gallery.Gallery
import com.makentoshe.booruchan.booru.model.gallery.GalleryFactory

class PostOrderedInfinityGalleryFactory: GalleryFactory {

    override fun createGallery(viewModel: BooruViewModel): Gallery {
        return PostOrderedInfinityGallery(viewModel)
    }


}