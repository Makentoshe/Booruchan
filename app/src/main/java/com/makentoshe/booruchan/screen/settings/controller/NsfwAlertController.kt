package com.makentoshe.booruchan.screen.settings.controller

import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.screen.settings.fragment.SettingsNsfwAlertFragment
import com.makentoshe.booruchan.screen.settings.model.setOnDialogResultListener

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
        val alertFragment = SettingsNsfwAlertFragment()
        alertFragment.show(fragmentManager, alertFragment::class.java.simpleName)
        alertFragment.setOnDialogResultListener {
            onCancelListener = { result(false) }
            onPositiveButtonClick = { result(true) }
            onNegativeButtonClick = { result(false) }
        }
    }
}