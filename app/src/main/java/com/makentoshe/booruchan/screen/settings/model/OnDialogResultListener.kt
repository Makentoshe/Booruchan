package com.makentoshe.booruchan.screen.settings.model

import com.makentoshe.booruchan.screen.settings.fragment.SettingsNsfwAlertFragment

interface OnDialogResultListener {
    /**
     * Calls when dialog was canceled
     */
    fun onCancel()

    /**
     * Calls when dialog was dismissed
     */
    fun onDismiss()

    /**
     * Calls when positive button was clicked
     */
    fun onPositiveButtonClick()

    /**
     * Calls when negative button was clicked
     */
    fun onNegativeButtonClick()

    companion object {
        fun create() = OnDialogResultListenerImpl()
    }

    class OnDialogResultListenerImpl : OnDialogResultListener {

        var onCancelListener: (() -> Unit)? = null
        var onDismissListener: (() -> Unit)? = null
        var onPositiveButtonClick: (() -> Unit)? = null
        var onNegativeButtonClick: (() -> Unit)? = null

        override fun onCancel() = onCancelListener?.invoke() ?: Unit

        override fun onDismiss() = onDismissListener?.invoke() ?: Unit

        override fun onPositiveButtonClick() = onPositiveButtonClick?.invoke() ?: Unit

        override fun onNegativeButtonClick() = onNegativeButtonClick?.invoke() ?: Unit
    }
}


fun SettingsNsfwAlertFragment.setOnDialogResultListener(
    init: OnDialogResultListener.OnDialogResultListenerImpl.() -> Unit
) {
    val listener = OnDialogResultListener.OnDialogResultListenerImpl()
    listener.init()
    onDialogResultListener = listener
}
