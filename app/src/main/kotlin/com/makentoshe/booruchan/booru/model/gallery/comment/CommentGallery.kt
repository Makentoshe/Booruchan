package com.makentoshe.booruchan.booru.model.gallery.comment

import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.gallery.Gallery
import org.jetbrains.anko.*

class CommentGallery: Gallery {

    override fun createView(context: @AnkoViewDslMarker _FrameLayout): View = with(context) {
        linearLayout {
            backgroundResource = R.color.PurpleAssent200
        }.lparams(matchParent, matchParent)
    }
}