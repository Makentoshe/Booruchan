package com.makentoshe.booruchan.danbooru.network

abstract class DanbooruRequest {

    val host = "https://danbooru.donmai.us"

    abstract val url: String
}