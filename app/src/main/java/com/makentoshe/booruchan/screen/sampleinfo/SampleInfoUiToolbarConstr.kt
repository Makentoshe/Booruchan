package com.makentoshe.booruchan.screen.sampleinfo

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

class SampleInfoUiToolbarConstr(
    @IdRes private val id: Int,
    @StringRes private val text: Int
) : AnkoComponent<_RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        relativeLayout {
            id = this@SampleInfoUiToolbarConstr.id
            visibility = View.GONE
            textView(text) {
                textColorResource = style.toolbar.onPrimaryColorRes
                setPadding(dip(16), dip(16), 0, dip(20))
            }.lparams { alignParentLeft() }
        }.lparams(matchParent, matchParent)
    }
}