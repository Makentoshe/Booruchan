package com.makentoshe.booruchan.feature.boorupost.domain

data class BoorupostsResponse(
    val request: BoorupostsRequest,
    val body: BoorupostsBody,
)

sealed interface BoorupostsBody {
    data class Json(val string: String) : BoorupostsBody
    data class Xml(val string: String) : BoorupostsBody
}