package com.makentoshe.settings.screen.controller

import androidx.fragment.app.FragmentManager
import com.makentoshe.settings.screen.model.OnDialogResultListener
import com.makentoshe.settings.screen.fragment.SettingsNsfwAlertFragment

/** Controller displays alert dialog. */
class NsfwControllerAlert(private val fm: FragmentManager) {

    /** Display alert with attached listener */
    fun displayAlert(listener: OnDialogResultListener) {
        SettingsNsfwAlertFragment.show(fm, listener)
    }

    class Factory {
        fun build(fm: FragmentManager): NsfwControllerAlert {
            return NsfwControllerAlert(fm)
        }
    }
}