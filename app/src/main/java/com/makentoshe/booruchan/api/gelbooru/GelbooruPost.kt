package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.post.PostTagsParser
import com.makentoshe.booruchan.api.component.post.Rating
import com.makentoshe.booruchan.api.component.post.Rating.Companion.parseRating

class GelbooruPost(
    private val postTagsParser: PostTagsParser,
    override val raw: Map<String, String> = emptyMap(),
    override val id: Long = raw["id"]?.toLong() ?: -1L,
    override val score: Int = raw["score"]?.toInt() ?: -1,
    override val previewUrl: String = raw["preview_url"] ?: "",
    override val sampleUrl: String = raw["sample_url"] ?: "",
    override val fileUrl: String = raw["file_url"] ?: "",
    override val creatorId: Long = raw["creator_id"]?.toLong() ?: -1L,
    override val rating: Rating = parseRating(raw["rating"]),
    override val tags: Array<Tag> = postTagsParser.parse(raw["tags"] ?: ""),
    override val previewHeight: Int = raw["preview_height"]?.toInt() ?: -1,
    override val previewWidth: Int = raw["preview_width"]?.toInt() ?: -1,
    override val source: String = raw["source"] ?: ""
) : Post