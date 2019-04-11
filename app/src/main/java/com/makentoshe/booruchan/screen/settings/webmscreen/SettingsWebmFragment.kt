package com.makentoshe.booruchan.screen.settings.webmscreen

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.model.arguments

class SettingsWebmFragment : Fragment() {

    private var position: Int
        set(value) = arguments().putInt(POSITION, value)
        get() = arguments!!.getInt(POSITION)

    companion object {
        private const val POSITION = "Position"
        fun create(position: Int) = SettingsWebmFragment().apply {
            this.position = position
        }
    }
}