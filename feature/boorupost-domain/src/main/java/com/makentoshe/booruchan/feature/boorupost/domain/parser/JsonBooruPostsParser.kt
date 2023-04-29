package com.makentoshe.booruchan.feature.boorupost.domain.parser

import com.makentoshe.booruchan.feature.boorupost.domain.entity.NetworkBooruPost

interface JsonBooruPostsParser {
    fun parse(json: String): List<NetworkBooruPost>
}

class BooruPostsJsonParserNotFoundException(json: String) : Exception(
    "Could not find valid parser for json: $json"
)
