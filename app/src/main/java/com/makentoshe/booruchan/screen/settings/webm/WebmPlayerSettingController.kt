package com.makentoshe.booruchan.screen.settings.webm

import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import com.makentoshe.booruchan.R
import org.jetbrains.anko.find

class WebmPlayerSettingController(
    private val stateController: WebmPlayerStateController
) {

    fun bindView(view: View) {
        val webmPlayingRoot = view.find<View>(R.id.setting_webm_on_place)
        val webmPlayingTrigger = view.find<CheckBox>(R.id.setting_webm_on_place_checkbox)
        //set default value
        webmPlayingTrigger.isChecked = stateController.state

        webmPlayingTrigger.setOnCheckedChangeListener(::onStateChanged)

        webmPlayingRoot.setOnClickListener {
            webmPlayingTrigger.isChecked = !stateController.state
        }
    }

    private fun onStateChanged(trigger: CompoundButton, newState: Boolean) {
        if (newState) stateController.enable() else stateController.disable()
    }
}