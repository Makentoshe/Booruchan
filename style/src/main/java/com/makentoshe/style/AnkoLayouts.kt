package com.makentoshe.style

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.FrameLayout
import com.google.android.material.button.MaterialButton
import org.jetbrains.anko.custom.ankoView

fun ViewManager.materialButton(theme: Int = 0, init: AnkoMaterialButton.() -> Unit): AnkoMaterialButton {
    return ankoView({ AnkoMaterialButton(it) }, theme, init)
}

class AnkoMaterialButton(context: Context) : MaterialButton(context) {
    fun <T : View> T.lparams(
        width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
        init: FrameLayout.LayoutParams.() -> Unit
    ): T {
        val layoutParams = FrameLayout.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T : View> T.lparams(
        width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = FrameLayout.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }
}
