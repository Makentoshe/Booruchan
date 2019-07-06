package com.makentoshe.boorusamplesview.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer

/** Interface for image decoding from [ByteArray] to a [Bitmap] */
interface ImageDecoder {
    /** Performs decoding */
    fun decode(byteArray: ByteArray): Bitmap
}

/**
 * Default Android image decoder uses [BitmapFactory] or [ImageDecoder] for api 28 and higher
 */
class AndroidImageDecoder : ImageDecoder {
    override fun decode(byteArray: ByteArray): Bitmap {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = android.graphics.ImageDecoder.createSource(ByteBuffer.wrap(byteArray))
            return android.graphics.ImageDecoder.decodeBitmap(source)
        }
        return BitmapFactory.decodeStream(ByteArrayInputStream(byteArray))
    }
}
