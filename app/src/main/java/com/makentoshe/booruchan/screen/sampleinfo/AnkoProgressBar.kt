package com.makentoshe.booruchan.screen.sampleinfo

import android.graphics.PorterDuff
import androidx.annotation.IdRes
import com.makentoshe.booruchan.Booruchan
import org.jetbrains.anko.*

class AnkoProgressBar(@IdRes private val id: Int) : AnkoComponent<_RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        progressBar {
            id = this@AnkoProgressBar.id
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