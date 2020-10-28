package com.makentoshe.booruchan.gelbooru.post.deserialize

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.makentoshe.booruchan.core.deserialize.CollectionDeserializeException
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

interface GelbooruPostsDeserializer {
    fun deserializePosts(string: String): Result<GelbooruPostsDeserialize<*>>
}

class XmlGelbooruPostsDeserializer : GelbooruPostsDeserializer {

    override fun deserializePosts(string: String): Result<XmlGelbooruPostsDeserialize> = try {
        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        val results = jsoup.getElementsByTag("post").map(::deserializePost)
        Result.success(GelbooruPostsDeserialize(results))
    } catch (exception: Exception) {
        Result.failure(CollectionDeserializeException(exception))
    }

    private val xmlPostDeserializer = XmlGelbooruPostDeserializer()
    private fun deserializePost(element: Element): Result<XmlGelbooruPostDeserialize> =
        xmlPostDeserializer.deserializePost(element.toString())
}

class JsonGelbooruPostsDeserializer : GelbooruPostsDeserializer {

    override fun deserializePosts(string: String): Result<JsonGelbooruPostsDeserialize> = try {
        val results = JsonMapper().readValue<JsonNode>(string).map(::deserializePost)
        Result.success(JsonGelbooruPostsDeserialize(results))
    } catch (exception: Exception) {
        Result.failure(CollectionDeserializeException(exception))
    }

    private val jsonPostDeserializer = JsonGelbooruPostDeserializer()
    private fun deserializePost(element: JsonNode): Result<JsonGelbooruPostDeserialize> =
        jsonPostDeserializer.deserializePost(element.toString())
}
