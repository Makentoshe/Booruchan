package com.makentoshe.booruchan.view

import android.graphics.PorterDuff
import androidx.annotation.IdRes
import com.makentoshe.booruchan.style
import com.makentoshe.booruchan.style.getColorFromStyle
import org.jetbrains.anko.*

class AnkoProgressBar(@IdRes private val id: Int) : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        themedProgressBar(style.progress) {
            id = this@AnkoProgressBar.id
            isIndeterminate = true
            indeterminateDrawable.setColorFilter(
                getColorFromStyle(android.R.attr.indeterminateDrawable),
                PorterDuff.Mode.SRC_ATOP
            )
        }.lparams {
            centerInParent()
        }
    }
}