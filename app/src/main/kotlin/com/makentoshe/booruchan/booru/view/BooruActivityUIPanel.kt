package com.makentoshe.booruchan.booru.view

import android.view.Gravity
import android.view.View
import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.common.StyleableAnkoComponent
import com.makentoshe.booruchan.common.styles.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4._DrawerLayout

class BooruActivityUIPanel(style: Style,
                           private val viewModel: BooruViewModel,
                           private val dlContext: _DrawerLayout)
    : StyleableAnkoComponent<BooruActivity>(style) {

    override fun createView(ui: AnkoContext<BooruActivity>): View = with(dlContext) {
        frameLayout {
            backgroundResource = style.backgroundColor
            textView {
                text = "Panel"
            }
        }.lparams {
            width = dip(260)
            height = matchParent
            gravity = Gravity.START
        }
    }
}