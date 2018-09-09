package com.makentoshe.booruchan.booru.view

import android.view.View
import com.makentoshe.booruchan.StyleableAnkoComponent
import com.makentoshe.booruchan.styles.Style
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class BooruActivityUI(style: Style): StyleableAnkoComponent<BooruActivity>(style) {

    override fun createView(ui: AnkoContext<BooruActivity>): View = with(ui){
        verticalLayout {

            textView {
                text = "SAS"
            }

        }
    }



}