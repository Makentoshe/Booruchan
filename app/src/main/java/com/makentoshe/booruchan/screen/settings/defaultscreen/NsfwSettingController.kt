package com.makentoshe.booruchan.screen.settings.defaultscreen

import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.model.RequestCode
import com.makentoshe.booruchan.screen.settings.AppSettings
import org.jetbrains.anko.find

class NsfwSettingController(private val fragment: Fragment) {

    private val nsfwSetting by lazy {
        fragment.view?.find<CheckBox>(R.id.setting_nsfw_checkbox)!!
    }

    private val nsfwSettingRow by lazy {
        fragment.view?.find<View>(R.id.setting_nsfw)!!
    }

    private fun requireContext() = fragment.requireContext()

    fun onViewCreated() {
        //set default nsfw setting
        nsfwSetting.isChecked = AppSettings.getNsfw(requireContext())
        //set on nsfw settings click listeners
        nsfwSetting.setOnCheckedChangeListener { _, isChecked ->
            onCheck(isChecked)
        }
        nsfwSettingRow.setOnClickListener {
            val newCheckState = AppSettings.getNsfw(requireContext()).not()
            nsfwSetting.isChecked = newCheckState
        }
    }

    fun onResult(resultCode: Int) {
        if (resultCode == 0) {
            nsfwSetting.isChecked = false
            changeNsfwSetting(false)
        }
        if (resultCode == 1) {
            nsfwSetting.isChecked = true
            changeNsfwSetting(true)
            //no need display alert more
            AppSettings.setNsfwAlert(requireContext(), false)
        }
    }

    private fun onCheck(isChecked: Boolean) {
        if (isChecked) {
            if (AppSettings.getNsfwAlert(requireContext())) {
                showNsfwAlertDialog()
            } else {
                changeNsfwSetting(isChecked)
            }
        } else {
            if (AppSettings.getNsfwAlert(requireContext())) {
                //do nothing
            } else {
                changeNsfwSetting(isChecked)
            }
        }
    }

    private fun changeNsfwSetting(boolean: Boolean) {
        AppSettings.setNsfw(requireContext(), boolean)
        callTargetFragmentChangeListView()
    }

    private fun showNsfwAlertDialog() {
        val fragment = SettingsNsfwAlertFragment()
        fragment.setTargetFragment(this.fragment, RequestCode.settings)
        fragment.show(this.fragment.fragmentManager, SettingsNsfwAlertFragment::class.java.simpleName)
    }

    /* Call main fragment to change list with boorus */
    private fun callTargetFragmentChangeListView() {
        fragment.targetFragment!!.onActivityResult(RequestCode.settings, 0, null)
    }

}