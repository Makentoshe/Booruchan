package com.makentoshe.booruchan.posttags.view

import android.graphics.PorterDuff
import android.view.View
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

class PostTagsUiProgressBar : AnkoComponent<_RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<_RelativeLayout>): View =
        with(ui.owner) {
        progressBar {
            id = R.id.posttags_progressbar
            isIndeterminate = true
            indeterminateDrawable?.setColorFilter(
                style.toolbar.getPrimaryColor(context),
                PorterDuff.Mode.SRC_ATOP
            )
        }.lparams {
            centerInParent()
        }
    }
}