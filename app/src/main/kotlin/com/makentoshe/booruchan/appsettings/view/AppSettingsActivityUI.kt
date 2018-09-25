package com.makentoshe.booruchan.appsettings.view

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.widget.*
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.appsettings.AppSettingsViewModel
import com.makentoshe.booruchan.common.StyleableAnkoComponent
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.common.forLollipop
import com.makentoshe.booruchan.common.settings.application.AppSettings
import com.makentoshe.booruchan.common.settings.application.AppSettingsSave
import com.makentoshe.booruchan.common.styles.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.titleResource
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.sdk25.coroutines.onItemSelectedListener

class AppSettingsActivityUI(style: Style)
    : StyleableAnkoComponent<AppSettingsActivity>(style) {

    override fun createView(ui: AnkoContext<AppSettingsActivity>): View = with(ui) {
        val viewModel = ViewModelProviders.of(ui.owner)[AppSettingsViewModel::class.java]
        verticalLayout {
            id = R.id.appsettings_main
            createToolbar()
                    .setSupportActionBar(ui.owner)
                    .setHomeIcon(style.toolbarForegroundColor, ui.owner)
            createStyleSettingStroke(viewModel, ui.owner)
        }
    }

    @SuppressLint("NewApi")
    private fun _LinearLayout.createToolbar(): Toolbar {
        return toolbar {
            id = R.id.appsettings_toolbar
            titleResource = R.string.app_settings_title
            setTitleTextColorResource(style.toolbarForegroundColor)
            backgroundColorResource = style.toolbarBackgroundColor
            forLollipop {
                elevation = dip(4).toFloat()
            }
            lparams {
                width = matchParent
                height = dip(style.dpToolbarHeight)
            }
        }
    }

    private fun _LinearLayout.createStyleSettingStroke(viewModel: AppSettingsViewModel, activity: Activity) {
        linearLayout {
            id = R.id.appsettings_style
            orientation = LinearLayout.HORIZONTAL
            createSettingTitle(R.string.color_theme)
            createSettingValueSpinner()
                    .setSpinnerAdapter(viewModel.createStyleSpinnerAdapter(context))
                    .setSelectedItem(Style.getStyleIndex(activity.getAppSettings().getStyle().styleId))
                    .setOnItemSelectedListener { adapter, _, pos, _ ->
                        onStyleSelected(activity, adapter?.getItemAtPosition(pos) as String)
                    }
        }.lparams {
            width = matchParent
            height = dip(style.dpToolbarHeight)
        }
    }

    private fun onStyleSelected(activity: Activity, styleTitle: String) {
        val sharedPreferences = activity.getSharedPreferences(AppSettings.NAME, Context.MODE_PRIVATE)
        val appSettingsSave = AppSettingsSave(sharedPreferences, activity.getAppSettings())
        val style = Style.getStyleByName(styleTitle)
        if (activity.getAppSettings().getStyle().styleId != style.styleId) {
            activity.getAppSettings().setStyle(style.styleId)
            appSettingsSave.saveStyle()
            activity.recreate()
        }
    }

    private fun _LinearLayout.createSettingTitle(@StringRes titleResource: Int) {
        textView {
            textResource = titleResource
            leftPadding = dip(8)
            gravity = Gravity.CENTER_VERTICAL
            textSize = dip(12).toFloat()
        }.lparams {
            width = wrapContent
            height = matchParent
            weight = 7f
        }
    }

    private fun _LinearLayout.createSettingValueSpinner(): Spinner {
        return spinner {
            gravity = Gravity.CENTER_VERTICAL
        }.lparams {
            width = wrapContent
            height = wrapContent
            weight = 3f
        }
    }

    private fun Spinner.setSelectedItem(styleIndex: Int): Spinner {
        setSelection(styleIndex)
        return this
    }

    private fun Spinner.setSpinnerAdapter(spinnerAdapter: SpinnerAdapter): Spinner {
        adapter = spinnerAdapter
        return this
    }

    private fun Spinner.setOnItemSelectedListener(
            listener: (adapter: AdapterView<*>?, view: View?, pos: Int, id: Long) -> Unit): Spinner {
        onItemSelectedListener {
            onItemSelected { adapterView, view, i, l ->
                listener.invoke(adapterView, view, i, l)
            }
        }
        return this
    }


}