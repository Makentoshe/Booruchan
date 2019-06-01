package com.makentoshe.booruchan.api

interface BooruFactory {
    fun buildBooru(`class`: Class<out Booru>): Booru
}