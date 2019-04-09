package com.makentoshe.booruchan.screen.settings

import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import org.jetbrains.anko.find

class WebMPlayingOnPlaceController(private val fragment: Fragment) {

    private val checkbox by lazy {
        fragment.view?.find<CheckBox>(R.id.setting_webm_on_place_checkbox)!!
    }

    private val view by lazy {
        fragment.view?.find<View>(R.id.setting_webm_on_place)!!
    }

    private val context = fragment.requireContext()

    fun onViewCreated() {
        //set default setting
        checkbox.isChecked = AppSettings.getStreamingDownload(context)
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            onCheck(isChecked)
        }
        view.setOnClickListener {
            checkbox.isChecked = AppSettings.getStreamingDownload(context).not()
        }
    }

    private fun onCheck(isChecked: Boolean) = if (isChecked) check() else uncheck()

    private fun check() {
        AppSettings.setWebmPlayingOnPlace(context, true)
    }

    private fun uncheck() {
        AppSettings.setWebmPlayingOnPlace(context, false)
    }
}