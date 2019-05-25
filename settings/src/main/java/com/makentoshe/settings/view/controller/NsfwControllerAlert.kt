package com.makentoshe.settings.view.controller

import androidx.fragment.app.FragmentManager
import com.makentoshe.settings.view.OnDialogResultListener
import com.makentoshe.settings.view.fragment.SettingsNsfwAlertFragment

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