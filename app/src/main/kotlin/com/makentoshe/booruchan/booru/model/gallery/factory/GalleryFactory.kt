package com.makentoshe.booruchan.booru.model.gallery.factory

import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.booru.model.gallery.Gallery
import com.makentoshe.booruchan.booru.view.BooruActivityUI
import com.makentoshe.booruchan.common.api.Boor
import java.lang.IllegalArgumentException

interface GalleryFactory {

    fun createGallery(activity: AppCompatActivity): Gallery

    companion object {

        fun createFactory(@BooruActivityUI.Gallery gallery: Int, booru: Boor): GalleryFactory {
            return when (gallery) {
                BooruActivityUI.GALLERY_POST_ORD_INF -> PostOrderedInfinityGalleryFactory(booru)
//                BooruActivityUI.GALLERY_COMMENT -> CommentGalleryFactory()
                else -> throw IllegalArgumentException(gallery.toString())
            }
        }
    }

}