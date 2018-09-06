package com.makentoshe.booruchan.start

import android.support.v4.content.ContextCompat
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.StyleableAnkoComponent
import com.makentoshe.booruchan.styles.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.titleResource
import org.jetbrains.anko.appcompat.v7.toolbar

class StartActivityView(style: Style) : StyleableAnkoComponent<StartActivity>(style) {

    override fun createView(ui: AnkoContext<StartActivity>) = with(ui) {
        verticalLayout {

            val toolbar = toolbar {
                setTitleTextColor(ContextCompat.getColor(ui.ctx, style.toolbarTextColor))
                id = R.id.activity_start_toolbar
                titleResource = R.string.app_name
                backgroundColorResource = style.toolbarBackgroundColor
            }.lparams {
                width = matchParent
                height = dip(style.dpToolbarHeight)
            }
            ui.owner.setSupportActionBar(toolbar)

            textView {
                padding = dip(8)
                text = "Sup, its just a start"
            }
        }
    }
}