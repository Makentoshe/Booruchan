package com.makentoshe.booruchan.booru.model.gallery.factory

import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.booru.model.gallery.Gallery
import com.makentoshe.booruchan.booru.view.BooruActivityUI
import java.lang.IllegalArgumentException

interface GalleryFactory {

    fun createGallery(activity: AppCompatActivity): Gallery

    companion object {

        fun createFactory(@BooruActivityUI.Gallery gallery: Int): GalleryFactory {
            return when (gallery) {
                BooruActivityUI.GALLERY_POST -> PostOrderedInfinityGalleryFactory()
//                BooruActivityUI.GALLERY_COMMENT -> CommentGalleryFactory()
                else -> throw IllegalArgumentException(gallery.toString())
            }
        }
    }

}