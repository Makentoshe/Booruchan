package com.makentoshe.booruchan.booru.model.gallery

import android.view.View
import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.common.styles.Style
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko._FrameLayout

interface Gallery {

    fun createView(context: @AnkoViewDslMarker _FrameLayout, galleryViewModel: GalleryViewModel): View

    fun onSearchStarted(): (String?) -> (Unit)
}