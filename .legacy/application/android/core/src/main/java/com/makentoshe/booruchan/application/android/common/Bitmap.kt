package com.makentoshe.booruchan.application.android.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer

/**
 * Method for decoding [ByteArray] to android [Bitmap].
 *
 * We using ImageDecoder for 29 and higher api and default BitmapFactory otherwise.
 */
fun ByteArray.toBitmap(): Bitmap? {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(ByteBuffer.wrap(this)))
    } else {
        BitmapFactory.decodeByteArray(this, 0, size)
    }
}
