package com.makentoshe.boorupostview.model

import android.content.Context
import org.jetbrains.anko.displayMetrics
import org.jetbrains.anko.px2dip

class ItemsCountCalculator {

    fun getItemsCountInRow(context: Context) = with(context) {
        return@with px2dip(displayMetrics.widthPixels) / 100
    }

    fun getItemsCountInColumn(context: Context) = with(context) {
        val actionBarHeightDp = 56
        return@with (px2dip(displayMetrics.heightPixels) - actionBarHeightDp * 2) / 110
    }

    fun getItemsCountTotal(context: Context): Int {
        val w = getItemsCountInRow(context).toInt()
        val h = getItemsCountInColumn(context).toInt()
        return w * h
    }
}