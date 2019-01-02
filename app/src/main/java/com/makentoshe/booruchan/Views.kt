package com.makentoshe.booruchan

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7._Toolbar
import org.jetbrains.anko.appcompat.v7.toolbar

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