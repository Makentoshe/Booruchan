package com.makentoshe.settings.screen.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.settings.screen.model.OnDialogResultListener

/**
 * Dialog fragment displays a user agreement. User can agree with it or disagree.
 * If dialog cancels - user disagree.
 */
class SettingsNsfwAlertFragment : DialogFragment() {

    private var onDialogResultListener: OnDialogResultListener
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(LISTENER, value)
        get() = arguments!!.get(LISTENER) as OnDialogResultListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(com.makentoshe.settings.R.string.user_agreement)
        builder.setMessage(com.makentoshe.settings.R.string.user_agreement_content)
        builder.setPositiveButton(com.makentoshe.settings.R.string.agree) { _, _ ->
            onDialogResultListener.onPositiveButtonClick()
        }
        builder.setNegativeButton(com.makentoshe.settings.R.string.disagree) { _, _ ->
            onDialogResultListener.onNegativeButtonClick()
        }
        return builder.create()
    }

    override fun onCancel(dialog: DialogInterface) {
        onDialogResultListener.onCancel()
    }

    override fun onDismiss(dialog: DialogInterface) {
        onDialogResultListener.onDismiss()
    }

    companion object {
        private const val LISTENER = "Listener"
        /** Displays dialog fragment attached to [fragmentManager] and contains [listener]. */
        fun show(fragmentManager: FragmentManager, listener: OnDialogResultListener): SettingsNsfwAlertFragment {
            return SettingsNsfwAlertFragment().apply {
                this.onDialogResultListener = listener
                show(fragmentManager, this::class.java.simpleName)
            }
        }
    }
}