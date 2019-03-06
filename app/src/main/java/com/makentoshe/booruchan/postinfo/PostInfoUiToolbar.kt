package com.makentoshe.booruchan.postinfo

import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.updateLayoutParams
import com.makentoshe.booruchan.BackToolbarIcon
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.style.Style
import org.jetbrains.anko.*

class PostInfoUiToolbar(private val style: Style = Booruchan.INSTANCE.style) :
    AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        relativeLayout {
            id = R.id.postinfo_toolbar
            backgroundColorResource = style.toolbar.primaryColorRes
            elevation = dip(4).toFloat()
            BackToolbarIcon()
                .createView(AnkoContext.createDelegate(this))
                .updateLayoutParams<RelativeLayout.LayoutParams> {
                    addRule(RelativeLayout.ALIGN_PARENT_LEFT)
                    addRule(RelativeLayout.CENTER_VERTICAL)
                }
        }.lparams(matchParent, dip(56)) {
            alignParentTop()
        }
    }
}