package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.component.comment.Comment

class Rule34Comment(
    override val raw: Map<String, String> = emptyMap(),
    override val postId: Long = raw["post_id"]?.toLong() ?: -1L,
    override val body: String = raw["body"] ?: "",
    override val id: Long = raw["id"]?.toLong() ?: -1L,
    override val createdAt: String = raw["created_at"] ?: "",
    override val creator: String = raw["creator"] ?: "",
    override val creatorId: Long = raw["creator_id"]?.toLong() ?: -1L
) : Comment