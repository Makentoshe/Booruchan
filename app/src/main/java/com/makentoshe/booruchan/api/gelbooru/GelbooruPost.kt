package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.api.Tag

class GelbooruPost(
    override val raw: Map<String, String> = emptyMap(),
    override val id: Long = raw["id"]?.toLong() ?: -1L,
    override val score: Int = raw["score"]?.toInt() ?: -1,
    override val previewUrl: String = raw["preview_url"] ?: "",
    override val sampleUrl: String = raw["sample_url"] ?: "",
    override val fileUrl: String = raw["file_url"] ?: "",
    override val creatorId: Long = raw["creator_id"]?.toLong() ?: -1L,
    override val rating: Post.Rating = parseRating(raw["rating"]),
    override val tags: Array<Tag> = parseTags(raw["tags"] ?: ""),
    override val previewHeight: Int = raw["preview_height"]?.toInt() ?: -1,
    override val previewWidth: Int = raw["preview_width"]?.toInt() ?: -1,
    override val source: String = raw["source"] ?: ""
) : Post {

    companion object {
        private fun parseRating(str: String?): Post.Rating {
            return when (str) {
                "s" -> Post.Rating.SAFE
                "q" -> Post.Rating.QUESTIONABLE
                "e" -> Post.Rating.EXPLICIT
                else -> Post.Rating.UNSPECIFIED
            }
        }

        private fun parseTags(str: String): Array<Tag> {
            if (str.isBlank()) return arrayOf()
            val stags = str.split(" ").toTypedArray()
            return Array(stags.size) { GelbooruTag(title = stags[it]) }
        }
    }
}