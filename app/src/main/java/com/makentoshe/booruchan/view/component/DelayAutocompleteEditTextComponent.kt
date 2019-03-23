package com.makentoshe.booruchan.view.component

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.makentoshe.booruchan.view.delayAutoCompleteEditText
import org.jetbrains.anko.*

class DelayAutocompleteEditTextComponent : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui.owner) {
        delayAutoCompleteEditText {
            layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
            gravity = Gravity.TOP and Gravity.CENTER_HORIZONTAL
            setPadding(dip(8), dip(10), dip(36), dip(8))
            singleLine = true
            imeOptions = EditorInfo.IME_ACTION_SEARCH
//            setCursorColor(Color.BLACK)
        }
    }

    private fun EditText.setCursorColor(@ColorInt color: Int) {
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
}