package com.makentoshe.booruchan.screen.settings.defaultscreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.model.RequestCode
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.settings.AppSettings
import com.makentoshe.booruchan.screen.settings.defaultscreen.view.SettingsDefaultUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class SettingsDefaultFragment : Fragment() {

    private var position: Int
        set(value) = arguments().putInt(POSITION, value)
        get() = arguments!!.getInt(POSITION)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsDefaultUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initNsfwSetting(view)
    }

    private fun initNsfwSetting(view: View) {
        val nsfwRoot = view.find<View>(R.id.setting_nsfw)
        val nsfwTrigger = view.find<CheckBox>(R.id.setting_nsfw_checkbox)
        SettingViewCheckerNsfw(nsfwRoot, nsfwTrigger).let {
            it.targetFragment = this
            it.bind(requireContext())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RequestCode.settings) onSettingsResult(resultCode)
    }

    private fun onSettingsResult(resultCode: Int) {
        val nsfwTrigger = view!!.find<CheckBox>(R.id.setting_nsfw_checkbox)
        nsfwTrigger.isChecked = (resultCode == 1).ifTrue {
            //no need display alert more
            AppSettings.setNsfwAlert(requireContext(), false)
        }
    }

    companion object {
        private const val POSITION = "Position"
        fun create(position: Int) = SettingsDefaultFragment().apply {
            this.position = position
        }
    }

    private fun Boolean.ifTrue(action: Boolean.() -> Unit): Boolean {
        if (this) action(this)
        return this
    }

}