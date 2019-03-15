package com.makentoshe.booruchan.screen.settings

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.appSettings
import com.makentoshe.booruchan.style
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

class SettingsUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        themedRelativeLayout(style.default) {
            setOnClickListener { /* just handle on click event to avoid actions in background fragment*/ }
            SettingsUiToolbar().createView(AnkoContext.createDelegate(this))
            SettingsUiNSFW().createView(AnkoContext.createDelegate(this)).lparams(matchParent, dip(56)) {
                below(R.id.settings_toolbar)
            }
        }
    }
}

class SettingsUiToolbar : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        themedRelativeLayout(style.toolbar) {
            id = R.id.settings_toolbar
            themedToolbar(style.toolbar) {
                titleResource = R.string.app_settings
            }.lparams(matchParent, matchParent)
        }.lparams(matchParent, dip(56)) {
            alignParentTop()
        }
    }
}

class SettingsUiNSFW : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        linearLayout {
            id = R.id.setting_nsfw
            themedTextView(style.default) {
                textResource = R.string.enable_nsfw
                gravity = Gravity.CENTER_VERTICAL
                setPadding(dip(8), 0, 0, 0)
            }.lparams(height = matchParent) {
                weight = 9f
            }
            checkBox {
                id = R.id.setting_nsfw_checkbox
                gravity = Gravity.CENTER
            }.lparams(height = matchParent) {
                weight = 1f
            }
        }
    }
}

object AppSettings {

    fun setNSFW(context: Context, boolean: Boolean) {
        context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).edit()
            .putBoolean(NSFW, boolean)
            .apply()
    }

    fun getNSFW(context: Context): Boolean {
        return context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).getBoolean(NSFW, false)
    }

    private const val APPLICATION = "AppSettings"
    private const val NSFW = "NotSafeForWatching"
}