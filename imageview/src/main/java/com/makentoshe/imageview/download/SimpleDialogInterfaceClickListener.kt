package com.makentoshe.imageview.download

import android.content.DialogInterface

/** Simple click listener for a on click listener used in a dialog */
open class SimpleDialogInterfaceClickListener : DialogInterface.OnClickListener {

    private var onPositiveButtonClick: ((DialogInterface) -> Unit)? = null
    private var onNegativeButtonClick: ((DialogInterface) -> Unit)? = null
    private var onNeutralButtonClick: ((DialogInterface) -> Unit)? = null
    private var onItemClickListener: ((DialogInterface, Int) -> Unit)? = null

    override fun onClick(dialog: DialogInterface, which: Int) = when (which) {
        DialogInterface.BUTTON_POSITIVE -> onPositiveButtonClick(dialog)
        DialogInterface.BUTTON_NEGATIVE -> onNegativeButtonClick(dialog)
        DialogInterface.BUTTON_NEUTRAL -> onNeutralButtonClick(dialog)
        else -> onItemClickListener(dialog, which)
    }

    fun setOnPositiveButtonClickListener(l: (DialogInterface) -> Unit) {
        onPositiveButtonClick = l
    }

    private fun onPositiveButtonClick(dialog: DialogInterface) {
        onPositiveButtonClick?.invoke(dialog)
    }

    fun setOnNegativeButtonClickListener(l: (DialogInterface) -> Unit) {
        onNegativeButtonClick = l
    }

    private fun onNegativeButtonClick(dialog: DialogInterface) {
        onNegativeButtonClick?.invoke(dialog)
    }

    fun setOnNeutralButtonClickListener(l: (DialogInterface) -> Unit) {
        onNeutralButtonClick = l
    }

    private fun onNeutralButtonClick(dialog: DialogInterface) {
        onNeutralButtonClick?.invoke(dialog)
    }

    fun setOnItemClickListener(l: (DialogInterface, Int) -> Unit) {
        onItemClickListener = l
    }

    private fun onItemClickListener(dialog: DialogInterface, position: Int) {
        onItemClickListener?.invoke(dialog, position)
    }

}