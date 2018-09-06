package com.makentoshe.booruchan.appsettings

import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.StyleableAnkoComponent
import com.makentoshe.booruchan.styles.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.titleResource
import org.jetbrains.anko.appcompat.v7.toolbar

class AppSettingsActivityView(style: Style): StyleableAnkoComponent<AppSettingsActivity>(style) {

    override fun createView(ui: AnkoContext<AppSettingsActivity>): View = with(ui) {
        verticalLayout {
            val toolbar = toolbar {
                setTitleTextColor(ContextCompat.getColor(ui.ctx, style.toolbarTextColor))
                id = R.id.activity_appsettings_toolbar
                titleResource = R.string.app_settings_title
                backgroundColorResource = style.toolbarBackgroundColor
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    elevation = dip(4).toFloat()
                }
            }.lparams {
                width = matchParent
                height = dip(style.dpToolbarHeight)
            }
            ui.owner.setSupportActionBar(toolbar)
        }
    }

}