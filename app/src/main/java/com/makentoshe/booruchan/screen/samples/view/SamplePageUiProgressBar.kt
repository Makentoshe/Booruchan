package com.makentoshe.booruchan.screen.samples.view

import android.graphics.PorterDuff
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import com.makentoshe.booruchan.style.getColorFromStyle
import org.jetbrains.anko.*

class SamplePageUiProgressBar : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        relativeLayout {

            themedProgressBar(style.progress) {
                id = R.id.samples_progress
                isIndeterminate = true
                indeterminateDrawable.setColorFilter(
                    getColorFromStyle(android.R.attr.indeterminateDrawable),
                    PorterDuff.Mode.SRC_ATOP
                )
            }.lparams { centerInParent() }

        }.lparams {
            centerInParent()
        }
    }
}