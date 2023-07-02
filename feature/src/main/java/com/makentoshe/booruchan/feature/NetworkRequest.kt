package com.makentoshe.booruchan.feature

data class NetworkRequest(
    val url: String,
    val method: NetworkMethod = NetworkMethod.Get,
    val parameters: Map<String, String> = emptyMap(),
)