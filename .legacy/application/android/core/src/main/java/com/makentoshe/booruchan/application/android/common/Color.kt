package com.makentoshe.booruchan.application.android.common

import android.content.Context
import androidx.core.content.ContextCompat
import com.makentoshe.booruchan.application.android.core.R
import com.makentoshe.booruchan.core.tag.Tag
import com.makentoshe.booruchan.core.tag.Type

fun Tag.getColor(context: Context): Int = when (type) {
    Type.GENERAL -> ContextCompat.getColor(context, R.color.tag_general)
    Type.ARTIST -> ContextCompat.getColor(context, R.color.tag_artist)
    Type.CHARACTER -> ContextCompat.getColor(context, R.color.tag_character)
    Type.COPYRIGHT -> ContextCompat.getColor(context, R.color.tag_copyright)
    Type.METADATA -> ContextCompat.getColor(context, R.color.tag_metadata)
}
