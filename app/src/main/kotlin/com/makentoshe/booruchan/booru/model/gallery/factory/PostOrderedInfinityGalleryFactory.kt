package com.makentoshe.booruchan.booru.model.gallery.factory

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.booru.model.gallery.Gallery
import com.makentoshe.booruchan.booru.model.gallery.posts.PostOrderedInfinityGallery
import com.makentoshe.booruchan.booru.model.gallery.posts.PostOrderedInfinityViewModel

class PostOrderedInfinityGalleryFactory: GalleryFactory {

    override fun createGallery(activity: AppCompatActivity): Gallery {
        val viewModel = ViewModelProviders.of(activity)[PostOrderedInfinityViewModel::class.java]
        return PostOrderedInfinityGallery(viewModel)
    }


}