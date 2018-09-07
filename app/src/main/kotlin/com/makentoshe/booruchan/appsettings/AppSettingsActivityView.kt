package com.makentoshe.booruchan.appsettings

import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
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

            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                id = R.id.activity_appsettings_style

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
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    weight = 3f
                }
                val items = arrayOf("Astarte", "Shuvi", "S", "A", "S")
                spinner.adapter = ArrayAdapter<String>(ui.ctx,
                        android.R.layout.simple_spinner_dropdown_item, items)

                setListener(spinner)

            }.lparams {
                width = matchParent
                height = dip(style.dpToolbarHeight)
            }
        }
    }

    private fun setListener(spinner: Spinner) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                println(parent?.getItemAtPosition(position))
            }

        }
    }

}