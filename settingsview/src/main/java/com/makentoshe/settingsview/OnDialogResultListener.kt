package com.makentoshe.settingsview

/**
 * Interface used to allow run some code when dialog lifecycle event was clicked.
 */
interface OnDialogResultListener : java.io.Serializable {
    /**
     * Calls when dialog was dismissed. Always calls in the dialog lifecycle end.
     */
    fun onDismiss()

    /**
     * Calls when dialog was canceled.
     */
    fun onCancel()

    /**
     * Calls when positive button was clicked.
     */
    fun onPositiveButtonClick()

    /**
     * Calls when negative button was clicked.
     */
    fun onNegativeButtonClick()

    companion object {
        /** Returns a [OnDialogResultListener] instance */
        fun create() = OnDialogResultListenerImpl()
    }

    /**
     * Default [OnDialogResultListener] implementation.
     */
    class OnDialogResultListenerImpl : OnDialogResultListener {
        /** Will be invoked on cancel event */
        var onCancelListener: (() -> Unit)? = null
        /** Will be invoked on dismiss event */
        var onDismissListener: (() -> Unit)? = null
        /** Will be invoked on positive button event */
        var onPositiveButtonClick: (() -> Unit)? = null
        /** Will be invoked on negative button event */
        var onNegativeButtonClick: (() -> Unit)? = null

        override fun onCancel() = onCancelListener?.invoke() ?: Unit

        override fun onDismiss() = onDismissListener?.invoke() ?: Unit

        override fun onPositiveButtonClick() = onPositiveButtonClick?.invoke() ?: Unit

        override fun onNegativeButtonClick() = onNegativeButtonClick?.invoke() ?: Unit
    }
}