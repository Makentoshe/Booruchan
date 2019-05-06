package com.makentoshe.booruchan.screen.settings.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.settings.model.OnDialogResultListener
import com.makentoshe.booruchan.style

class SettingsNsfwAlertFragment : DialogFragment() {

    var onDialogResultListener: OnDialogResultListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext(), style.dialog)
        builder.setTitle(R.string.user_agreement)
        builder.setMessage(R.string.user_agreement_content)
        builder.setPositiveButton(R.string.agree) { _, _ ->
            onDialogResultListener?.onPositiveButtonClick()
        }
        builder.setNegativeButton(R.string.disagree) { _, _ ->
            onDialogResultListener?.onNegativeButtonClick()
        }
        return builder.create()
    }

    override fun onCancel(dialog: DialogInterface?) {
        onDialogResultListener?.onCancel()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        onDialogResultListener?.onDismiss()
    }
}
