package com.makentoshe.booruchan.screen.settings.model

interface OnDialogResultListener {
    fun onCancel()
    fun onDismiss()
    fun onPositiveButtonClick()
    fun onNegativeButtonClick()
}