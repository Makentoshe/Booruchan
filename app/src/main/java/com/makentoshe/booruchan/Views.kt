package com.makentoshe.booruchan

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7._Toolbar
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.custom.ankoView

fun _RelativeLayout.toolbarLayout(
    toolbarInit: (@AnkoViewDslMarker _Toolbar).() -> Unit = {},
    imageViewInit: (@AnkoViewDslMarker ImageView).() -> Unit = {}
) {
    toolbar { toolbarInit() }.lparams(width = matchParent) { alignWithParent = true }
    imageView { imageViewInit() }.lparams(height = dip(24)) {
        setMargins(0, 0, dip(16), 0)
        addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        addRule(RelativeLayout.CENTER_VERTICAL)
    }
}

fun hideKeyboard(context: Context, view: View) {
    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(view.windowToken, 0)
}

fun showKeyboard(context: Context, view: View) {
    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .showSoftInput(view, 0)
}

fun View.getDrawable(@DrawableRes id: Int) = context.getDrawable(id)

open class _ChipGroup(ctx: Context) : ChipGroup(ctx) {

    inline fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?,
        init: ChipGroup.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ChipGroup.LayoutParams(c!!, attrs!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?
    ): T {
        val layoutParams = ChipGroup.LayoutParams(c!!, attrs!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        init: ChipGroup.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ChipGroup.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = ChipGroup.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.LayoutParams?,
        init: ChipGroup.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.LayoutParams?
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?,
        init: ChipGroup.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ChipGroup.LayoutParams?,
        init: ChipGroup.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ChipGroup.LayoutParams?
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

}

fun ViewManager.chipGroup(init: _ChipGroup.() -> Unit) = ankoView({ _ChipGroup(it) }, 0, init)

fun ViewManager.chip(init: Chip.() -> Unit) = ankoView({ Chip(it) }, 0, init)
