package com.makentoshe.booruchan.screen.settings.common

import androidx.fragment.app.Fragment

/**
 * Displays alert dialog.
 */
class NsfwAlertController {

    /**
     * The parent fragment, from the dialog will be displayed.
     */
    lateinit var fragment: Fragment

    /**
     * Displays alert dialog.
     *
     * @param result is a function calls when decision was made with the result.
     */
    fun showAlert(result: (Boolean) -> Unit) {
        if (!this::fragment.isInitialized) result(false)

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