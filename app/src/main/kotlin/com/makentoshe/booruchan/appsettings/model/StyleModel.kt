package com.makentoshe.booruchan.appsettings.model

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.makentoshe.booruchan.appsettings.AppSettings
import com.makentoshe.booruchan.appsettings.AppSettingsSave
import com.makentoshe.booruchan.appsettings.view.RecreateableView
import com.makentoshe.booruchan.styles.Style

class StyleModel(private val context: Context,
                 private val appSettings: AppSettings,
                 private val appSettingsSave: AppSettingsSave,
                 private val recreateableView: RecreateableView) {

    fun setStyleSpinnerData(spinner: Spinner) {
        spinner.adapter = ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_dropdown_item, Style.arrayOfStyleNames)
        spinner.setSelection(Style.getStyleIndex(appSettings.getStyleVal()))
    }

    fun setStyleSelectListener(spinner: Spinner) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val styleVal =
                        Style.getStyleValByName(parent.getItemAtPosition(position) as String)
                if (appSettings.getStyleVal() != styleVal) {
                    appSettings.setStyle(styleVal)
                    appSettingsSave.saveStyle()
                    recreateableView.recreate()
                }
            }
        }
    }

}