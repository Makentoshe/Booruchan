package com.makentoshe.booruchan.screen.samples.view

import android.graphics.PorterDuff
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

class SamplePageUiProgress : AnkoComponent<_RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        progressBar {
            id = R.id.samples_progress
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