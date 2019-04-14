package com.makentoshe.booruchan.screen.settings.model

import android.content.Context
import android.view.View
import android.widget.CheckBox

abstract class SettingViewChecker(
    protected val root: View,
    protected val trigger: CheckBox
) : SettingView {

    override fun bind(context: Context) {
        trigger.setOnCheckedChangeListener { _, isChecked -> onStateChanged(context, isChecked) }
        setDefaultSetting(context)
    }

    abstract fun setDefaultSetting(context: Context)

    abstract fun onStateChanged(context: Context, newState: Boolean)

}