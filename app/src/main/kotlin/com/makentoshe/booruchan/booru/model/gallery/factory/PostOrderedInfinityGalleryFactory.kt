package com.makentoshe.booruchan.booru.model.gallery.factory

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.booru.model.gallery.Gallery
import com.makentoshe.booruchan.booru.model.gallery.posts.PostOrderedInfinityGallery
import com.makentoshe.booruchan.booru.model.gallery.posts.PostOrderedInfinityViewModel
import com.makentoshe.booruchan.common.api.Boor

class PostOrderedInfinityGalleryFactory(private val booru: Boor) : GalleryFactory {

    override fun createGallery(activity: AppCompatActivity): Gallery {
        val viewModel = ViewModelProviders
                .of(activity, PostOrderedInfinityViewModel
                        .PostOrderedInfinityViewModelFactory(booru))[PostOrderedInfinityViewModel::class.java]
        return PostOrderedInfinityGallery(viewModel)
    }


}