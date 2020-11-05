package com.makentoshe.booruchan.gelbooru.post.deserialize

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.makentoshe.booruchan.gelbooru.post.GelbooruPost
import com.makentoshe.booruchan.gelbooru.post.XmlGelbooruPost

interface GelbooruPostSerializer<Post: GelbooruPost> {
    fun serializePost(post: Post): String
}

class XmlGelbooruPostSerializer: GelbooruPostSerializer<XmlGelbooruPost> {

    private val mapper = XmlMapper()

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun serializePost(post: XmlGelbooruPost): String {
        return mapper.writeValueAsString(post)
    }
}