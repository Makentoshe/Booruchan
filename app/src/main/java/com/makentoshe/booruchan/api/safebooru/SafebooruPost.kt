package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.api.Tag

class SafebooruPost(
    override val raw: Map<String, String> = emptyMap(),
    override val id: Long = raw["id"]?.toLong() ?: -1L,
    override val score: Int = raw["score"]?.toInt() ?: -1,
    override val previewUrl: String = raw["preview_url"]?.let { "https:".plus(it) } ?: "",
    override val sampleUrl: String = raw["sample_url"]?.let { "https:".plus(it) } ?: "",
    override val fileUrl: String = raw["file_url"]?.let { "https:".plus(it) } ?: "",
    override val creatorId: Long = raw["creator_id"]?.toLong() ?: -1L,
    override val rating: Post.Rating = parseRating(raw["rating"]),
    override val tags: Array<Tag> = parseTags(raw["tags"] ?: "")
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
            return Array(stags.size) { SafebooruTag(title = stags[it]) }
        }
    }
}