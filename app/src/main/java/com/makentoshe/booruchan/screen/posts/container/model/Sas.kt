package com.makentoshe.booruchan.screen.posts.container.model

import android.content.Context
import org.jetbrains.anko.displayMetrics
import org.jetbrains.anko.px2dip

fun getItemsCountInRowF(context: Context) = with(context) {
    return@with px2dip(displayMetrics.widthPixels) / 100
}

fun getItemsCountInColumnF(context: Context) = with(context) {
    val actionBarHeightDp = 56
    return@with (px2dip(displayMetrics.heightPixels) - actionBarHeightDp * 2) / 110
}

fun getItemsCountInRequest(context: Context) = with(context) {
    val w = getItemsCountInRowF(context).toInt()
    val h = getItemsCountInColumnF(context).toInt()
    return@with w * h
}
