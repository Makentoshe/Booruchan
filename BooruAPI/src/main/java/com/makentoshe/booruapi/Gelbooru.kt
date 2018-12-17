package com.makentoshe.booruapi

import com.makentoshe.network.HttpClient
import com.makentoshe.parser.JsonTagParser
import com.makentoshe.parser.XmlPostsParser
import java.io.InputStream


class GelbooruApi : BooruApi {
    override fun getCustomRequest(request: String): String {
        return "https://gelbooru.com$request"
    }

    override fun getAutocompleteRequest(term: String): String {
        return getCustomRequest("/index.php?page=autocomplete&term=$term")
    }

    override fun getPostsRequest(count: Int, page: Int, tags: Set<Tag>): String {
        val strTags = StringBuilder().apply {
            tags.forEachIndexed { index, tag ->
                append(tag.name)
                if (index != tags.size - 1) append(" ")
            }
        }
        return getCustomRequest("/index.php?page=dapi&s=post&q=index&limit=$count&pid=$page&tags=$strTags")
    }
}

class Gelbooru(private val httpClient: HttpClient) : Booru(GelbooruApi()) {
    override fun customGet(request: String): InputStream {
        return httpClient.get(api.getCustomRequest(request)).stream()
    }

    override fun autocomplete(term: String): List<Tag> {
        return JsonTagParser().parse(httpClient.get(api.getAutocompleteRequest(term)).stream())
    }

    override fun getPosts(count: Int, page: Int, tags: Set<Tag>): Posts {
        return XmlPostsParser().parse(httpClient.get(api.getPostsRequest(count, page, tags)).stream())
    }
}