package com.makentoshe.booruchan.booru.model.content

import android.support.v4.app.Fragment
import android.view.View
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko._FrameLayout

interface Content {

    fun createView(contentViewModel: ContentViewModel): Fragment

    fun onSearchStarted(): (String?) -> (Unit)
}