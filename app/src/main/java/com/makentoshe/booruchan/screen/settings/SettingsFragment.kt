package com.makentoshe.booruchan.screen.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.appSettings
import org.jetbrains.anko.*

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return SettingsUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val nsfwSetting = view.find<CheckBox>(R.id.setting_nsfw_checkbox)
        nsfwSetting.setOnCheckedChangeListener { _, isChecked ->
            appSettings.setNSFW(requireContext(), isChecked)
        }
        nsfwSetting.isChecked = appSettings.getNSFW(requireContext())
    }
}