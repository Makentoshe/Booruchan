package post

//interface GelbooruPostsDeserializer<out Posts : GelbooruPosts<*>> {
//    fun deserializePosts(response: GelbooruPostsResponse.Success): Posts
//}
//
//class XmlGelbooruPostsDeserializer : GelbooruPostsDeserializer<XmlGelbooruPosts> {
//
//    override fun deserializePosts(response: GelbooruPostsResponse.Success): XmlGelbooruPosts {
//        val body = Jsoup.parse(response.string).body()
//        val posts = body.getElementsByTag("posts")
//        val list = body.getElementsByTag("post")
//        return XmlGelbooruPosts(count(posts), offset(posts), posts(list))
//    }
//
//    private fun count(posts: Elements) = posts.attr("count").toInt()
//
//    private fun offset(posts: Elements) = posts.attr("offset").toInt()
//
//    private fun posts(list: Elements): List<XmlGelbooruPost> = list.map { post ->
//        XmlGelbooruPost(
//            postId(post),
//            postScore(post),
//            postMd5(post),
//            postRating(post),
//            postSource(post),
//            postCreationTime(post),
//            postFull(post),
//            postPreview(post),
//            postSample(post),
//            postTags(post),
//            postComments(post),
//            postChange(post)
//        )
//    }
//
//    private fun postId(post: Element) = post.attr("id").toInt()
//
//    private fun postScore(post: Element) = post.attr("score").toInt()
//
//    private fun postMd5(post: Element) = post.attr("md5")
//
//    private fun postRating(post: Element) = when (val rating = post.attr("rating")) {
//        "s" -> Rating.SAFE
//        "q" -> Rating.QUESTIONABLE
//        "e" -> Rating.EXPLICIT
//        else -> error("Could not define \"rating=$rating\" attribute")
//    }
//
//    private fun postSource(post: Element) = post.attr("source")
//
//    private fun postComments(post: Element) = post.attr("has_comments").toBoolean()
//
//    private fun postCreationTime(post: Element) = time(post.attr("created_at"))
//
//    private fun postChange(post: Element) = post.attr("change")
//
//    private fun postPreview(post: Element): PreviewImage {
//        return previewImage(postPreviewUrl(post), postPreviewHeight(post), postPreviewWidth(post))
//    }
//
//    private fun postPreviewUrl(post: Element) = post.attr("preview_url")
//
//    private fun postPreviewWidth(post: Element) = post.attr("preview_width").toIntOrNull()
//
//    private fun postPreviewHeight(post: Element) = post.attr("preview_height").toIntOrNull()
//
//    private fun postSample(post: Element): SampleImage {
//        return sampleImage(postSampleUrl(post), postSampleHeight(post), postSampleWidth(post))
//    }
//
//    private fun postSampleUrl(post: Element) = post.attr("sample_url")
//
//    private fun postSampleWidth(post: Element) = post.attr("sample_width").toIntOrNull()
//
//    private fun postSampleHeight(post: Element) = post.attr("sample_height").toIntOrNull()
//
//    private fun postFull(post: Element): FullImage {
//        return fullImage(postFullUrl(post), postFullHeight(post), postFullWidth(post))
//    }
//
//    private fun postFullUrl(post: Element) = post.attr("file_url")
//
//    private fun postFullWidth(post: Element) = post.attr("width").toIntOrNull()
//
//    private fun postFullHeight(post: Element) = post.attr("height").toIntOrNull()
//
//    private fun postTags(post: Element) = tags(post.attr("tags").split(" ").map(::text).toSet())
//}
//
//class JsonGelbooruPostsDeserializer : GelbooruPostsDeserializer<JsonGelbooruPosts> {
//
//    override fun deserializePosts(response: GelbooruPostsResponse.Success): JsonGelbooruPosts {
//        val list = Json { isLenient = true }.decodeFromString<List<Map<String, String?>>>(response.string)
//        return JsonGelbooruPosts(posts(list))
//    }
//
//    private fun posts(list: List<Map<String, String?>>): List<JsonGelbooruPost> = list.map(::post)
//
//    private fun post(map: Map<String, String?>): JsonGelbooruPost {
//        return JsonGelbooruPost(
//            postId(map),
//            postScore(map),
//            postMd5(map),
//            postRating(map),
//            postSource(map),
//            postCreationTime(map),
//            postFull(map),
//            postSample(map),
//            postPreview(map),
//            postTags(map),
//            postOwner(map),
//            postParentId(map),
//            postChange(map)
//        )
//    }
//
//    private fun postId(map: Map<String, String?>) = (map["id"] ?: postError("id")).toInt()
//
//    private fun postScore(map: Map<String, String?>) = (map["score"] ?: postError("score")).toInt()
//
//    private fun postRating(map: Map<String, String?>) = when (map["rating"]) {
//        "s" -> Rating.SAFE
//        "q" -> Rating.QUESTIONABLE
//        "e" -> Rating.EXPLICIT
//        else -> postError("rating")
//    }
//
//    private fun postMd5(map: Map<String, String?>) = (map["hash"] ?: postError("hash"))
//
//    private fun postSource(map: Map<String, String?>) = (map["source"] ?: postError("source"))
//
//    private fun postCreationTime(map: Map<String, String?>) = map["created_at"]?.let(::time) ?: postError("created_at")
//
//    private fun postTags(map: Map<String, String?>) =
//        (map["tags"] ?: postError("tags")).split(" ").map(::text).toSet().let(::tags)
//
//    private fun postError(attribute: String): Nothing = error("Could not find \"$attribute\" attribute")
//
//    private fun postFull(map: Map<String, String?>): FullImage {
//        val url = map["file_url"] ?: postError("file_url")
//        val height = map["height"]?.toIntOrNull() ?: postError("height")
//        val width = map["width"]?.toIntOrNull() ?: postError("width")
//        return fullImage(url, height, width)
//    }
//
//    private fun postSample(map: Map<String, String?>): SampleImage {
//        val directory = map["directory"] ?: postError("directory")
//        val imageString = File(map["image"] ?: postError("image"))
//        val extension = when (imageString.extension) {
//            "png" -> "jpg"
//            else -> imageString.extension
//        }
//        val imageFile = File("sample_${imageString.name.dropLast(extension.length.inc())}.$extension")
//        val url = File("https://img2.gelbooru.com/samples/$directory", "$imageFile")
//        return sampleImage(url.toString())
//    }
//
//    private fun postPreview(map: Map<String, String?>): PreviewImage {
//        val directory = map["directory"] ?: postError("directory")
//        val imageString = map["image"] ?: postError("image")
//        val imageFile = File("thumbnail_$imageString")
//        val url = File("https://img1.gelbooru.com/thumbnails/$directory", "${imageFile.name}.jpg")
//        return previewImage(url.toString())
//    }
//
//    private fun postOwner(map: Map<String, String?>) = (map["owner"] ?: postError("owner"))
//
//    private fun postParentId(map: Map<String, String?>) = map["parent_id"]?.toIntOrNull()
//
//    private fun postChange(map: Map<String, String?>) = map["change"]?.toIntOrNull() ?: postError("change")
//}
//