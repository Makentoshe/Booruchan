package com.makentoshe.style.element

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

abstract class ViewStyle(@JvmField @ColorRes val primaryColorRes: Int,
                         @JvmField @ColorRes val onPrimaryColorRes: Int,
                         @JvmField @ColorRes val secondaryColorRes: Int = primaryColorRes,
                         @JvmField @ColorRes val onSecondaryColorRes: Int = onPrimaryColorRes) {

    fun getPrimaryColor(context: Context) =
        ContextCompat.getColor(context, primaryColorRes)

    fun getOnPrimaryColor(context: Context) =
        ContextCompat.getColor(context, onPrimaryColorRes)

    fun getSecondaryColor(context: Context) =
        ContextCompat.getColor(context, secondaryColorRes)

    fun getOnSecondaryColor(context: Context) =
        ContextCompat.getColor(context, onSecondaryColorRes)

}