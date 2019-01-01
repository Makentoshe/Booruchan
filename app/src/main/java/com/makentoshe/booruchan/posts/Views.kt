package com.makentoshe.booruchan.posts

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.ViewManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.makentoshe.booruchan.Style
import org.jetbrains.anko.custom.ankoView

fun EditText.setCursorColor(@ColorInt color: Int) {
    val fCursorDrawableRes = TextView::class.java.getDeclaredField("mCursorDrawableRes")
    fCursorDrawableRes.isAccessible = true
    val mCursorDrawableRes = fCursorDrawableRes.getInt(this)
    val fEditor = TextView::class.java.getDeclaredField("mEditor")
    fEditor.isAccessible = true
    val editor = fEditor.get(this)
    val clazz = editor.javaClass
    val fCursorDrawable = clazz.getDeclaredField("mCursorDrawable")
    fCursorDrawable.isAccessible = true
    val drawables = arrayOfNulls<Drawable>(2)
    drawables[0] = ContextCompat.getDrawable(context, mCursorDrawableRes)
    drawables[1] = ContextCompat.getDrawable(context, mCursorDrawableRes)
    drawables[0]!!.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    drawables[1]!!.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    fCursorDrawable.set(editor, drawables)
}

fun ViewManager.delayAutoCompleteEditText(init: DelayAutocompleteEditText.() -> Unit) =
    ankoView({ DelayAutocompleteEditText(it) }, 0, init)

fun ImageView.setToolbarIcon(style: Style, @DrawableRes icon: Int) {
    setImageResource(icon)
    setColorFilter(style.toolbar.getOnPrimaryColor(context), PorterDuff.Mode.SRC_ATOP)
}