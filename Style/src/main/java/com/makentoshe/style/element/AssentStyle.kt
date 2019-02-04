package com.makentoshe.style.element

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

class AssentStyle(
    @JvmField @ColorRes val assentColorRes: Int,
    @JvmField @ColorRes val onAssentColorRes: Int,
    @JvmField @ColorRes val secondaryAssentColorRes: Int,
    @JvmField @ColorRes val onSecondaryAssentColorRes: Int) {

    fun getAssentColor(context: Context) =
        ContextCompat.getColor(context, assentColorRes)

    fun getOnAssentColor(context: Context) =
        ContextCompat.getColor(context, onAssentColorRes)

    fun getSecondaryAssentColor(context: Context) =
        ContextCompat.getColor(context, secondaryAssentColorRes)

    fun getOnSecondaryAssentColor(context: Context) =
        ContextCompat.getColor(context, onSecondaryAssentColorRes)

}