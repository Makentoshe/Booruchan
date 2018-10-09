package com.makentoshe.booruchan.common.api.gelbooru

import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.HttpClient
import com.makentoshe.booruchan.common.api.Posts
import com.makentoshe.booruchan.common.api.parser.AutocompleteSearchParser
import com.makentoshe.booruchan.common.api.parser.HtmlParser
import com.makentoshe.booruchan.common.api.parser.PostParser
import java.io.Serializable

class Gelbooru : Boor(GelbooruRequestAPI()), Serializable {

    override fun getBooruName(): String {
        return "Gelbooru"
    }

    override fun convertLocalTimeToDefault(time: String): String {
        val split = time.split(" ")
        val year = split[5]
        val month = split[1]
        val day = split[2]
        val daytime = split[3]
        return "$day $month $year in $daytime"
    }

    override suspend fun getAutocompleteSearchVariations(
            httpClient: HttpClient, term: String): List<String> {
        val result = httpClient.get(getApi().getAutocompleteSearchRequest(term)).stream()
        return AutocompleteSearchParser().parse(result)
    }

    override suspend fun getPostsByTags(
            limit: Int,
            tags: String,
            page: Int,
            httpClient: HttpClient): Posts<out com.makentoshe.booruchan.common.api.entity.Post> {
        val result = httpClient.get(getApi().getPostsByTagsRequest(limit, tags, page)).stream()
        return PostParser(Post::class.java).parsePosts(result)
    }

    override suspend fun getListOfLastCommentedPosts(
            page: Int, httpClient: HttpClient):
            ArrayList<Pair<com.makentoshe.booruchan.common.api.entity.Post, List<com.makentoshe.booruchan.common.api.entity.Comment>>> {
        val result = httpClient.get(getApi().getListOfCommentsViewRequest(page)).stream()
        return HtmlParser.parseComments(result, this::class.java)
    }

    override suspend fun getPostById(
            postId: Int, httpClient: HttpClient): com.makentoshe.booruchan.common.api.entity.Post {
        val result = httpClient.get(getApi().getPostByIdRequest(postId)).stream()
        return PostParser(Post::class.java).parsePosts(result).getPost(0)
    }

    class Post : com.makentoshe.booruchan.common.api.entity.Post() {

        var previewHeight: Int = -1

        var previewWidth: Int = -1

        lateinit var status: String

        var sampleWidth: Int = -1

        lateinit var change: String

        var width: Int = -1

        var hasNotes: Boolean = false

        var sampleHeight: Int = -1

        var height: Int = -1

        var hasChildren: Boolean = false

        lateinit var source: String

        lateinit var md5: String

        var parentId: Int = -1

        override fun fill(attributes: Map<String, String>) {
            super.fill(attributes)
            //create Entry
            val entrySet = attributes.entries
            //for each attribute
            for ((key, value) in entrySet) {
                when (key) {
                    "md5" -> {
                        md5 = value
                    }
                    "source" -> {
                        source = value
                    }
                    "height" -> {
                        height = value.toInt()
                    }
                    "width" -> {
                        width = value.toInt()
                    }
                    "sample_height" -> {
                        sampleHeight = value.toInt()
                    }
                    "sample_width" -> {
                        sampleWidth = value.toInt()
                    }
                    "preview_height" -> {
                        previewHeight = value.toInt()
                    }
                    "preview_width" -> {
                        previewWidth = value.toInt()
                    }
                    "has_notes" -> {
                        hasNotes = value.toBoolean()
                    }
                    "status" -> {
                        status = value
                    }
                    "change" -> {
                        change = value
                    }
                    "has_children" -> {
                        hasChildren = value.toBoolean()
                    }
                    "parent_id" -> {
                        try {
                            parentId = value.toInt()
                        } catch (e: Exception) {
                        }
                    }
                }
            }
        }


    }

    class Comment(raw: Map<String, Any>) : com.makentoshe.booruchan.common.api.entity.Comment(raw)

}