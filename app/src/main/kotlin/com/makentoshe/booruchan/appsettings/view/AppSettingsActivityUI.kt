package com.makentoshe.booruchan.appsettings.view

import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.widget.*
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.StyleableAnkoComponent
import com.makentoshe.booruchan.appsettings.presenter.AppSettingsActivityPresenter
import com.makentoshe.booruchan.common.styles.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.titleResource
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.sdk25.coroutines.onItemSelectedListener

class AppSettingsActivityUI(style: Style, private val presenter: AppSettingsActivityPresenter)
    : StyleableAnkoComponent<AppSettingsActivity>(style) {

    override fun createView(ui: AnkoContext<AppSettingsActivity>): View = with(ui) {
        verticalLayout {
            createToolbar(this) {
                ui.owner.setSupportActionBar(it)
                val indicator = createHomeIcon(style.toolbarForegroundColor, ui.owner)
                ui.owner.supportActionBar?.setHomeAsUpIndicator(indicator)
                ui.owner.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                ui.owner.supportActionBar?.setHomeButtonEnabled(true)
            }
            createStyleChangeSetting(this)
        }
    }

    private inline fun createToolbar(linearLayoutContext: @AnkoViewDslMarker _LinearLayout, then: (Toolbar) -> Unit) {
        with(linearLayoutContext) {
            then.invoke(toolbar {
                setTitleTextColor(ContextCompat.getColor(linearLayoutContext.context, style.toolbarForegroundColor))
                id = R.id.activity_appsettings_toolbar
                titleResource = R.string.app_settings_title
                backgroundColorResource = style.toolbarBackgroundColor
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    elevation = dip(4).toFloat()
                }
                lparams {
                    width = matchParent
                    height = dip(style.dpToolbarHeight)
                }
            })
        }
    }

    private fun createStyleChangeSetting(linearLayoutContext: @AnkoViewDslMarker _LinearLayout) {
        with(linearLayoutContext) {
            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                textView {
                    text = "Color theme"
                    leftPadding = dip(8)
                    gravity = Gravity.CENTER_VERTICAL
                    textSize = dip(12).toFloat()
                }.lparams {
                    width = wrapContent
                    height = matchParent
                    weight = 7f
                }
                val spinner = spinner {
                    gravity = Gravity.CENTER_VERTICAL

                    onItemSelectedListener {
                        onItemSelected { adapterView, _, position, _ ->
                            presenter.setStyleOnSelect(adapterView!!, position)
                        }
                    }

                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    weight = 3f
                }
                presenter.setStyleSpinnerData(spinner)

            }.lparams {
                width = matchParent
                height = dip(style.dpToolbarHeight)
            }
        }
    }
}