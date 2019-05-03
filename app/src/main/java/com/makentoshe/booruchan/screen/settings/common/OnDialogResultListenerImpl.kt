package com.makentoshe.booruchan.screen.settings.common

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

fun SettingsNsfwAlertFragment.setOnDialogResultListener(init: OnDialogResultListenerImpl.() -> Unit) {
    val listener = OnDialogResultListenerImpl()
    listener.init()
    onDialogResultListener = listener
}
