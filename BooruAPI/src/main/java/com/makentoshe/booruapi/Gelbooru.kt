package com.makentoshe.booruapi

import com.makentoshe.network.HttpClient
import com.makentoshe.parser.JsonTagParser
import com.makentoshe.parser.XmlPostsParser
import java.io.InputStream
import java.io.Serializable


class GelbooruApi : BooruApi, Serializable {
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

    override fun getPreviewRequest(previewUrl: String) = previewUrl
}

class Gelbooru(private val httpClient: HttpClient) : Booru(GelbooruApi()), Serializable {
    override val title: String
        get() = javaClass.simpleName

    override fun customGet(request: String): InputStream {
        return httpClient.get(api.getCustomRequest(request)).stream()
    }

    override fun autocomplete(term: String): List<Tag> {
        return JsonTagParser().parse(httpClient.get(api.getAutocompleteRequest(term)).stream())
    }

    override fun getPosts(request: PostRequest): Posts {
        return XmlPostsParser()
            .parse(httpClient.get(api.getPostsRequest(request.count, request.page, request.tags)).stream())
    }

    override fun getPreview(previewUrl: String): InputStream {
        return httpClient.get(previewUrl).stream()
    }
}