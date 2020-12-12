package com.makentoshe.booruchan.application.android.screen.samples

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.makentoshe.booruchan.application.android.R

internal const val REQUEST_CODE_SAVE_IMAGE = 1

class ImageDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setItems(R.array.samples_menu) { _, which -> onItemClick(which) }
        }.create()
    }

    private fun onItemClick(which: Int) = when (which) {
        0 -> targetFragment?.onActivityResult(REQUEST_CODE_SAVE_IMAGE, Activity.RESULT_OK, null)
        else -> Unit
    }
}