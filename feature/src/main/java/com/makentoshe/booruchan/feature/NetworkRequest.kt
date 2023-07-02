package com.makentoshe.booruchan.feature

data class NetworkRequest(
    val method: NetworkMethod,
    val url: String,
)