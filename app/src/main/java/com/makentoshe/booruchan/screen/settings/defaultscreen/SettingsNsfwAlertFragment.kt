package com.makentoshe.booruchan.screen.settings.defaultscreen

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.model.RequestCode
import com.makentoshe.booruchan.style

class SettingsNsfwAlertFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext(), style.dialog)
        builder.setTitle(R.string.user_agreement)
        builder.setMessage(R.string.user_agreement_content)
        builder.setPositiveButton(R.string.agree) { _, _ -> onResult(1) }
        builder.setNegativeButton(R.string.disagree) { _, _ -> onResult(0) }
        builder.setOnCancelListener { onResult(0) }
        builder.setOnDismissListener { onResult(0) }
        return builder.create()
    }

    private fun onResult(result: Int) {
        targetFragment!!.onActivityResult(RequestCode.settings, result, null)
        dialog.dismiss()
    }
}