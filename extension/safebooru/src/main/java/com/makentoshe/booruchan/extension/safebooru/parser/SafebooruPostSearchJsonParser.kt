package com.makentoshe.booruchan.extension.safebooru.parser

import com.makentoshe.booruchan.extension.entity.NetworkPost
import com.makentoshe.booruchan.extension.parser.PostSearchParser
import com.makentoshe.booruchan.extension.safebooru.entity.NetworkSafebooruPosts
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SafebooruPostSearchJsonParser: PostSearchParser {
    override fun parse(string: String): List<NetworkPost> {
        return Json.decodeFromString<NetworkSafebooruPosts>(string)
    }
}