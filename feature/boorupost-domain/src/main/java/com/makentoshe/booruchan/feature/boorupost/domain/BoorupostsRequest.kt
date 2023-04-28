package com.makentoshe.booruchan.feature.boorupost.domain

data class BoorupostsRequest(
   val host: String,
    // How many posts we want to retrieve. There might be a hard limit for posts per request.
    val count: Int,
    // The page number for pagination
    val page: Int,
    // The tags to search for
    val tags: String,
)

