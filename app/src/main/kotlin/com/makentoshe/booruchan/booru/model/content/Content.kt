package com.makentoshe.booruchan.booru.model.content

import android.view.View
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko._FrameLayout

interface Content {

    fun createView(context: @AnkoViewDslMarker _FrameLayout, contentViewModel: ContentViewModel): View

    fun onSearchStarted(): (String?) -> (Unit)
}