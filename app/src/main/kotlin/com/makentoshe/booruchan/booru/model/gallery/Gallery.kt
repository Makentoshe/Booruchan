package com.makentoshe.booruchan.booru.model.gallery

import android.view.View
import com.makentoshe.booruchan.booru.BooruViewModel
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko._FrameLayout

interface Gallery {

    fun createView(context: @AnkoViewDslMarker _FrameLayout, galleryViewModel: GalleryViewModel): View

}