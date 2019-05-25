package com.makentoshe.style

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes

fun View.styleAttr(@AttrRes attribute: Int) = context.styleAttr(attribute)

fun Context.styleAttr(@AttrRes attribute: Int) = theme.styleAttr(attribute)

fun Resources.Theme.styleAttr(@AttrRes attribute: Int): TypedValue {
    val typedValue = TypedValue()
    if (!resolveAttribute(attribute, typedValue, false)) {
        throw IllegalArgumentException("Failed to resolve attribute: $attribute")
    }
    return typedValue
}
