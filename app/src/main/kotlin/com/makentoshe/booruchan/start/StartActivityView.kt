package com.makentoshe.booruchan.start

import android.view.Gravity
import org.jetbrains.anko.*

class StartActivityView : AnkoComponent<StartActivity> {

    override fun createView(ui: AnkoContext<StartActivity>) = with(ui) {
        verticalLayout {
            padding = dip(8)
            textView {
                text = "Sup, its just a start"
            }
        }
    }
}