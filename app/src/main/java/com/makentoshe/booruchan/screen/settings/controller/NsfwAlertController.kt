package com.makentoshe.booruchan.screen.settings.controller

import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.screen.settings.fragment.SettingsNsfwAlertFragment
import com.makentoshe.booruchan.screen.settings.model.OnDialogResultListener

/**
 * Displays alert dialog.
 *
 * @param fragmentManager is a parent fragment from the dialog will be displayed.
 */
class NsfwAlertController(private val fragmentManager: FragmentManager) {

    /**
     * Displays alert dialog.
     *
     * @param result is a function calls when decision was made with the result.
     */
    fun showAlert(result: (Boolean) -> Unit) {
        val listener = OnDialogResultListener.create()
        listener.onCancelListener = { result(false) }
        listener.onNegativeButtonClick = { result(false) }
        listener.onPositiveButtonClick = { result(true) }

        SettingsNsfwAlertFragment.show(fragmentManager, listener)
    }
}