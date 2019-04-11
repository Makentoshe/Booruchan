package com.makentoshe.booruchan.screen.sampleinfo.view

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*

class SampleInfoUiToolbarConstr(
    @IdRes private val id: Int,
    @StringRes private val text: Int,
    private val action: _RelativeLayout.()-> Unit = {}
) : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        relativeLayout {
            id = this@SampleInfoUiToolbarConstr.id
            visibility = View.GONE
            themedTextView(style.toolbar) {
                textResource = this@SampleInfoUiToolbarConstr.text
                setPadding(dip(16), dip(16), 0, dip(20))
            }.lparams { alignParentLeft() }
            action()
        }.lparams(matchParent, matchParent)
    }
}