package com.makentoshe.booruchan.api

import android.content.Context

interface BooruFactory {
    fun buildBooru(`class`: Class<out Booru>, context: Context): Booru
}