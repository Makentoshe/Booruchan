package com.makentoshe.booruchan.screen.settings.defaultscreen

import android.content.Context
import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.model.RequestCode
import com.makentoshe.booruchan.screen.settings.AppSettings
import com.makentoshe.booruchan.screen.settings.model.SettingViewChecker

class SettingViewCheckerNsfw(root: View, trigger: CheckBox) : SettingViewChecker(root, trigger) {

    lateinit var targetFragment: Fragment

    override fun setDefaultSetting(context: Context) {
        trigger.isChecked = AppSettings.getNsfw(context)
    }

    override fun onStateChanged(context: Context, newState: Boolean) {
        val shouldDisplayAlert = AppSettings.getNsfwAlert(context)
        if (newState) {
            if (shouldDisplayAlert) {
                displayAlertDialog()
            } else {
                changeSetting(context, newState)
            }
        } else {
            if (shouldDisplayAlert) {
                //do nothing
            } else {
                changeSetting(context, newState)
            }
        }
    }

    private fun changeSetting(context: Context, state: Boolean) {
        AppSettings.setNsfw(context, state)
    }

    private fun displayAlertDialog() {
        if (this::targetFragment.isInitialized.not()) return

        val fragment = SettingsNsfwAlertFragment()
        fragment.setTargetFragment(targetFragment, RequestCode.settings)
        fragment.show(targetFragment.fragmentManager, SettingsNsfwAlertFragment::class.java.simpleName)
    }

}