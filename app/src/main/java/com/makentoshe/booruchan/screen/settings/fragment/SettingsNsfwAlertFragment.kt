package com.makentoshe.booruchan.screen.settings.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.settings.model.OnDialogResultListener
import com.makentoshe.booruchan.style

class SettingsNsfwAlertFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext(), style.dialog)
        builder.setTitle(R.string.user_agreement)
        builder.setMessage(R.string.user_agreement_content)
        builder.setPositiveButton(R.string.agree) { _, _ ->
            onDialogResultListener?.onPositiveButtonClick()
            dialog.dismiss()
        }
        builder.setNegativeButton(R.string.disagree) { _, _ ->
            onDialogResultListener?.onNegativeButtonClick()
            dialog.dismiss()
        }
        builder.setOnCancelListener { onDialogResultListener?.onCancel() }
        builder.setOnDismissListener { onDialogResultListener?.onDismiss() }
        return builder.create()
    }

    var onDialogResultListener: OnDialogResultListener? = null

}
