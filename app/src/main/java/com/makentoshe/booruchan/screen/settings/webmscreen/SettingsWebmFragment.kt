package com.makentoshe.booruchan.screen.settings.webmscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.model.arguments
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class SettingsWebmFragment : Fragment() {

    private var position: Int
        set(value) = arguments().putInt(POSITION, value)
        get() = arguments!!.getInt(POSITION)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsWebmUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initWebmPlaying(view)
    }

    private fun initWebmPlaying(view: View) {
        val webmPlayingRoot = view.find<View>(R.id.setting_webm_on_place)
        val webmPlayingTrigger = view.find<CheckBox>(R.id.setting_webm_on_place_checkbox)
        SettingViewCheckerWebmPlaying(webmPlayingRoot, webmPlayingTrigger).bind(requireContext())
    }

    companion object {
        private const val POSITION = "Position"
        fun create(position: Int) = SettingsWebmFragment().apply {
            this.position = position
        }
    }
}