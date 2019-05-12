package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream

fun Drawable.toByteArray(): ByteArray {
    val bmp = toBitmap()
    val stream = ByteArrayOutputStream()
    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
    val byteArray = stream.toByteArray()
    bmp.recycle()
    return byteArray
}

