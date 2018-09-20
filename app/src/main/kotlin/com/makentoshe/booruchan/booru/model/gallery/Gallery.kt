package com.makentoshe.booruchan.booru.model.gallery

import android.view.View
import com.makentoshe.booruchan.booru.model.ContainerViewModel
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko._FrameLayout

interface Gallery {

    fun createView(context: @AnkoViewDslMarker _FrameLayout, containerViewModel: ContainerViewModel): View

    fun onSearchStarted(): (String?) -> (Unit)
}