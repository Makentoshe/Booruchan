package com.makentoshe.settingsview.view

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import org.jetbrains.anko.*

/**
 * Layout for setting configuration with boolean value used checkbox.
 */
class BooleanSettingUi(
    @IdRes private val mainId: Int,
    @IdRes private val checkboxId: Int,
    @StringRes private val textRes: Int
) : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui.owner) {
        relativeLayout {
            id = mainId
            lparams(matchParent, dip(56))

            createTextView()
            createCheckbox()
        }
    }

    private fun _RelativeLayout.createTextView() = textView(textRes) {
        gravity = Gravity.CENTER_VERTICAL
        setPadding(dip(8), 0, 0, 0)
    }.lparams(matchParent, matchParent) {
        startOf(checkboxId)
    }

    private fun _RelativeLayout.createCheckbox() = checkBox {
        id = checkboxId
        gravity = Gravity.CENTER
    }.lparams(height = matchParent) {
        marginEnd = dip(16)
        alignParentRight()
    }
}