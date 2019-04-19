package com.makentoshe.booruchan.screen.settings.controller.nsfw

import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import com.makentoshe.booruchan.R
import org.jetbrains.anko.find

/**
 * Controls on setting views touch gestures.
 *
 * @param alertController performs alert dialog controlling.
 * @param nsfwStateController performs setting controller on the AppSettings level.
 */
class NsfwSettingController(
    private val alertController: NsfwAlertController,
    private val nsfwStateController: NsfwStateController
) {

    /**
     * Listener calls each time when alert dialog returns negative result,
     * such as cancel, dismiss or negative button click.
     */
    private var onNegativeAlertListener: (() -> Unit)? = null

    /**
     * Setup any listeners and default values to the views.
     */
    fun bindView(view: View) {
        val nsfwTrigger = view.find<CheckBox>(R.id.setting_nsfw_checkbox)
        nsfwTrigger.setOnCheckedChangeListener(::onStateChanged)
        //set default value
        nsfwTrigger.isChecked = nsfwStateController.state

        onNegativeAlertListener = { nsfwTrigger.isChecked = false }

        val nsfwRoot = view.find<View>(R.id.setting_nsfw)
        nsfwRoot.setOnClickListener { onRootClick(nsfwTrigger) }
    }

    /**
     * Calls each time the root was clicked
     */
    private fun onRootClick(trigger: CheckBox) {
        val state = trigger.isChecked
        trigger.isChecked = !state
    }

    /**
     * Calls each time the nsfw setting was changed from the view using
     * onCheckedChangeListener
     */
    private fun onStateChanged(trigger: CompoundButton, newState: Boolean) {
        if (newState) checkNsfwAlert() else disableNsfw()
    }

    /**
     * Check the nsfw alert state and displays alert or enabling setting.
     */
    private fun checkNsfwAlert() {
        if (nsfwStateController.shouldShowAlert) showAlert() else enableNsfw()
    }

    /**
     * Displays alert dialog and change setting using the result.
     */
    private fun showAlert() {
        alertController.showAlert { result ->
            if (result) {
                enableNsfw()
                nsfwStateController.disableAlert()
            } else {
                disableNsfw()
                onNegativeAlertListener?.invoke()
            }
        }
    }

    private fun enableNsfw() = nsfwStateController.enable()

    private fun disableNsfw() = nsfwStateController.disable()

}