package com.makentoshe.booruchan.postinfo

import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.style.Style
import org.jetbrains.anko.*

class PostInfoUi(private val style: Style = Booruchan.INSTANCE.style) : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        relativeLayout {
            backgroundColorResource = style.background.backgroundColorRes
            PostInfoUiToolbar().createView(AnkoContext.createDelegate(this))
            PostInfoUiContent().createView(AnkoContext.createDelegate(this))
        }
    }
}