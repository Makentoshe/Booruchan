package com.makentoshe.boorupostview.model

import android.content.Context
import org.jetbrains.anko.displayMetrics
import org.jetbrains.anko.px2dip

/**
 * Performs calculations with grid elements. Calculates a count in row, count in column, and total count.
 */
class ItemsCountCalculator {

    /** Calculate elements count in single row */
    fun getItemsCountInRow(context: Context) = with(context) {
        return@with px2dip(displayMetrics.widthPixels) / 100
    }

    /** Calculate elements count in single column */
    fun getItemsCountInColumn(context: Context) = with(context) {
        val actionBarHeightDp = 56
        return@with (px2dip(displayMetrics.heightPixels) - actionBarHeightDp * 2) / 110
    }

    /** Calculate total elements count in grid */
    fun getItemsCountTotal(context: Context): Int {
        val w = getItemsCountInRow(context).toInt()
        val h = getItemsCountInColumn(context).toInt()
        return w * h
    }
}