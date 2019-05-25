package com.makentoshe.settings.view.controller

import android.view.View
import android.widget.CheckBox
import com.makentoshe.settings.model.SettingController
import com.makentoshe.settings.view.OnDialogResultListener
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class NsfwController(
    private val nsfwSettingController: NsfwSettingController,
    private val nsfwAlertSettingController: NsfwAlertSettingController,
    private val nsfwControllerAlert: NsfwControllerAlert
) {
    private val dialogListener = OnDialogResultListener.create()

    fun bindView(view: View) {
        val nsfwTrigger = view.find<CheckBox>(com.makentoshe.settings.R.id.nsfw_setting_target)
        val nsfwRoot = view.find<View>(com.makentoshe.settings.R.id.nsfw_setting)
        nsfwTrigger.isChecked = nsfwSettingController.get()
        //change setting on trigger click
        nsfwTrigger.setOnCheckedChangeListener { _, isChecked -> onCheck(isChecked) }
        //change setting on root click
        nsfwRoot.onClick { nsfwTrigger.isChecked = !nsfwTrigger.isChecked }
        //change setting after alert result
        dialogListener.onPositiveButtonClick = {
            nsfwSettingController.set(true)
            nsfwTrigger.isChecked = true
        }
        dialogListener.onNegativeButtonClick = {
            nsfwSettingController.set(false)
            nsfwTrigger.isChecked = false
        }
        dialogListener.onCancelListener = {
            nsfwSettingController.set(false)
            nsfwTrigger.isChecked = false
        }
    }

    /**
     * Calls when setting was changed
     */
    private fun onCheck(newState: Boolean) {
        if (nsfwAlertSettingController.get() && newState) onAlert() else nsfwSettingController.set(newState)
    }

    /**
     * Displays alert dialog with information.
     */
    private fun onAlert() = nsfwControllerAlert.displayAlert(dialogListener)

    class Factory {
        fun build(controller: SettingController<Boolean>, alertController: NsfwControllerAlert): NsfwController {
            val nsfwController = NsfwSettingController.Factory().build(controller)
            val nsfwAlertController = NsfwAlertSettingController.Factory().build(controller)
            return NsfwController(nsfwController, nsfwAlertController, alertController)
        }
    }
}
