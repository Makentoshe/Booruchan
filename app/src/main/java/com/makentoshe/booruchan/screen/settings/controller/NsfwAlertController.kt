package com.makentoshe.booruchan.screen.settings.controller

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.screen.settings.model.setOnDialogResultListener
import com.makentoshe.booruchan.screen.settings.fragment.SettingsNsfwAlertFragment

/**
 * Displays alert dialog.
 *
 * @param fragment is a parent fragment from the dialog will be displayed.
 */
class NsfwAlertController(private val fragment: Fragment) {

    /**
     * Displays alert dialog.
     *
     * @param result is a function calls when decision was made with the result.
     */
    fun showAlert(result: (Boolean) -> Unit) {
        val alertFragment = SettingsNsfwAlertFragment()
        alertFragment.show(fragment.fragmentManager, alertFragment::class.java.simpleName)
        alertFragment.setOnDialogResultListener {
            onCancelListener = { result(false) }
            onDismissListener = { result(false) }
            onPositiveButtonClick = { result(true) }
            onNegativeButtonClick = { result(false) }
        }
    }
}