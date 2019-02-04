package com.makentoshe.style.element

import android.content.Context
import androidx.core.content.ContextCompat

class BackgroundStyle(@JvmField val backgroundColorRes: Int, @JvmField val onBackgroundColorRes: Int) {

    fun getBackgroundColor(context: Context) =
        ContextCompat.getColor(context, backgroundColorRes)

    fun getOnBackgroundColor(context: Context) =
        ContextCompat.getColor(context, onBackgroundColorRes)

}