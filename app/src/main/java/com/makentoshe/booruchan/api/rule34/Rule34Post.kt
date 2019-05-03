package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.post.PostTagsParser
import com.makentoshe.booruchan.api.component.post.Rating
import com.makentoshe.booruchan.api.component.tag.Tag

class Rule34Post(
    private val postTagsParser: PostTagsParser,
    override val raw: Map<String, String> = emptyMap(),
    override val id: Long = raw["id"]?.toLongOrNull() ?: -1L,
    override val score: Int = raw["score"]?.toIntOrNull() ?: -1,
    override val previewUrl: String = raw["preview_url"] ?: "",
    override val sampleUrl: String = raw["sample_url"] ?: "",
    override val fileUrl: String = raw["file_url"] ?: "",
    override val creatorId: Long = raw["creator_id"]?.toLongOrNull() ?: -1L,
    override val rating: Rating = Rating.parseRating(raw["rating"]),
    override val tags: Array<Tag> = postTagsParser.parse(raw["tags"] ?: ""),
    override val previewHeight: Int = raw["preview_height"]?.toIntOrNull() ?: -1,
    override val previewWidth: Int = raw["preview_width"]?.toIntOrNull() ?: -1,
    override val source: String = raw["source"] ?: "",
    override val hasComments: Boolean = raw["has_comments"]?.toBoolean() ?: false
) : Post