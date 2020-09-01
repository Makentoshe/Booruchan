package network

import Time
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import post.*
import text
import time

data class XmlGelbooruPosts(val count: Int, val offset: Int, val posts: List<XmlGelbooruPost>)

data class XmlGelbooruPost(
    val postId: Int,
    val score: Int,
    val md5: String,
    val rating: Rating,
    val source: String,
    val hasComments: Boolean,
    val creationTime: Time,
    val fullImage: FullImage,
    val previewImage: PreviewImage,
    val sampleImage: SampleImage,
    val tags: Tags,
    val change: String
)

class XmlGelbooruPostsDeserializer {

    fun deserializePosts(response: XmlGelbooruPostsResponse.Success): XmlGelbooruPosts {
        val body = Jsoup.parse(response.string).body()
        val posts = body.getElementsByTag("posts")
        val list = body.getElementsByTag("post")
        return XmlGelbooruPosts(count(posts), offset(posts), posts(list))
    }

    private fun count(posts: Elements) = posts.attr("count").toInt()

    private fun offset(posts: Elements) = posts.attr("offset").toInt()

    private fun posts(list: Elements): List<XmlGelbooruPost> = list.map { post ->
        XmlGelbooruPost(
            postId(post),
            postScore(post),
            postMd5(post),
            postRating(post),
            postSource(post),
            postComments(post),
            postCreationTime(post),
            postFull(post),
            postPreview(post),
            postSample(post),
            postTags(post),
            postChange(post)
        )
    }

    private fun postId(post: Element) = post.attr("id").toInt()

    private fun postScore(post: Element) = post.attr("score").toInt()

    private fun postMd5(post: Element) = post.attr("md5")

    private fun postRating(post: Element) = when (val rating = post.attr("rating")) {
        "s" -> Rating.SAFE
        "q" -> Rating.QUESTIONABLE
        "e" -> Rating.EXPLICIT
        else -> error("Could not define \"rating=$rating\" attribute")
    }

    private fun postSource(post: Element) = post.attr("source")

    private fun postComments(post: Element) = post.attr("has_comments").toBoolean()

    private fun postCreationTime(post: Element) = time(post.attr("created_at"))

    private fun postChange(post: Element) = post.attr("change")

    private fun postPreview(post: Element): PreviewImage {
        return previewImage(postPreviewUrl(post), postPreviewHeight(post), postPreviewWidth(post))
    }

    private fun postPreviewUrl(post: Element) = post.attr("preview_url")

    private fun postPreviewWidth(post: Element) = post.attr("preview_width").toIntOrNull()

    private fun postPreviewHeight(post: Element) = post.attr("preview_height").toIntOrNull()

    private fun postSample(post: Element): SampleImage {
        return sampleImage(postSampleUrl(post), postSampleHeight(post), postSampleWidth(post))
    }

    private fun postSampleUrl(post: Element) = post.attr("sample_url")

    private fun postSampleWidth(post: Element) = post.attr("sample_width").toIntOrNull()

    private fun postSampleHeight(post: Element) = post.attr("sample_height").toIntOrNull()

    private fun postFull(post: Element): FullImage {
        return fullImage(postFullUrl(post), postFullHeight(post), postFullWidth(post))
    }

    private fun postFullUrl(post: Element) = post.attr("file_url")

    private fun postFullWidth(post: Element) = post.attr("width").toIntOrNull()

    private fun postFullHeight(post: Element) = post.attr("height").toIntOrNull()

    private fun postTags(post: Element) = tags(post.attr("tags").split(" ").map(::text).toSet())
}

