package com.makentoshe.booruchan.feature.boorupost.data

import com.makentoshe.booruchan.feature.boorupost.domain.entity.NetworkBooruPost
import com.makentoshe.booruchan.feature.boorupost.domain.entity.NetworkGelbooruPosts
import com.makentoshe.booruchan.feature.boorupost.domain.entity.NetworkSafebooruPosts
import com.makentoshe.booruchan.feature.boorupost.domain.parser.BooruPostsJsonParserNotFoundException
import com.makentoshe.booruchan.feature.boorupost.domain.parser.JsonBooruPostsParser
import dagger.Provides
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Named

@Named("Gelbooru02")
class Gelbooru02BooruPostsJsonParser @Inject constructor(): JsonBooruPostsParser {
    override fun parse(json: String): List<NetworkBooruPost> {
        try {
            return Json.decodeFromString<NetworkSafebooruPosts>(json)
        } catch (Exception: Exception) {

        }

        try {
            return Json.decodeFromString<NetworkGelbooruPosts>(json).posts
        } catch (Exception: Exception) {

        }

        throw BooruPostsJsonParserNotFoundException(json)
    }
}
