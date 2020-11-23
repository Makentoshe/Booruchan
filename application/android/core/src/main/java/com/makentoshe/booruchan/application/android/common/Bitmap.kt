package com.makentoshe.booruchan.application.android.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayInputStream

/** Method for decoding [ByteArray] to android [Bitmap]. */
fun ByteArray.toBitmap(): Bitmap? = BitmapFactory.decodeStream(ByteArrayInputStream(this))
