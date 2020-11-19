package com.makentoshe.booruchan.application.android.common

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * Converts dp unit to equivalent pixels, depending on device density.
 *
 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun Context.dp2px(dp: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)


/**
 * Converts dp unit to equivalent pixels, depending on device density.
 *
 * @param id A dimension resource in dp (density independent pixels) unit. Which we need to convert into pixels
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun Context.dp2px(id: Int) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, resources.getDimension(id), resources.displayMetrics)

/**
 * Converts device specific pixels to density independent pixels.
 *
 * @param px A value in px (pixels) unit. Which we need to convert into db
 * @return A float value to represent dp equivalent to px value
 */
fun Context.px2dp(px: Float) =
    px / (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

/**
 * Converts device specific pixels to density independent pixels.
 *
 * @param id A dimension resource in px (pixels) unit. Which we need to convert into db
 * @return A float value to represent dp equivalent to px value
 */
fun Context.px2dp(id: Int) =
    resources.getDimension(id) / (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
