package com.makentoshe.booruchan.screen.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.appSettings
import com.makentoshe.booruchan.model.RequestCode
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class SettingsFragment : Fragment() {

    private val nsfwSetting by lazy {
        view?.find<CheckBox>(R.id.setting_nsfw_checkbox)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        appSettings.setNsfwAlert(requireContext(), true)
        return SettingsUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //set default setting
        nsfwSetting.isChecked = appSettings.getNsfw(requireContext())
        nsfwSetting.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (appSettings.getNsfwAlert(requireContext())) {
                    showNsfwAlertDialog()
                } else {
                    appSettings.setNsfw(requireContext(), isChecked)
                }
            } else {
                if (appSettings.getNsfwAlert(requireContext())) {
                    //do nothing
                } else {
                    appSettings.setNsfw(requireContext(), isChecked)
                }
            }
        }
    }

    private fun showNsfwAlertDialog() {
        val fragment = SettingsNsfwAlertFragment()
        fragment.setTargetFragment(this, RequestCode.settings)
        fragment.show(fragmentManager, SettingsNsfwAlertFragment::class.java.simpleName)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RequestCode.settings) {
            if (resultCode == 0) {
                nsfwSetting.isChecked = false
                appSettings.setNsfw(requireContext(), false)
            }
            if (resultCode == 1) {
                nsfwSetting.isChecked = true
                appSettings.setNsfw(requireContext(), true)
                //no need display alert more
                appSettings.setNsfwAlert(requireContext(), false)
            }
        }
    }
}