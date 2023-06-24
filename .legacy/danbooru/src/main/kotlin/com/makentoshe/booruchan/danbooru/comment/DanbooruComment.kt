package com.makentoshe.booruchan.danbooru.comment

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.makentoshe.booruchan.core.Time
import com.makentoshe.booruchan.core.comment.Comment
import com.makentoshe.booruchan.core.time
import java.io.Serializable

interface DanbooruComment : Comment, Serializable {
    val score: Int
    val updaterId: Int?
    val updationTime: Time?
    val isBumped: Boolean
    val isDeleted: Boolean
    val isSticky: Boolean
}

data class XmlDanbooruComment(
    @JsonProperty("id", required = true)
    @JacksonXmlProperty(localName = "id")
    override val commentId: Int, // 1
    @JsonProperty("post-id", required = true)
    @JacksonXmlProperty(localName = "post-id")
    override val postId: Int, // 162
    @JsonProperty("body", required = true)
    @JacksonXmlProperty(localName = "body")
    override val text: String, // Yoshiharu Makita - Radical Temptation[巻田佳春] RADICAL☆てんぷて～しょん
    @JsonProperty("created-at", required = true)
    @JacksonXmlProperty(localName = "created-at")
    private val rawCreationTime: String, // 2005-05-25T16:31:50.000-04:00
    @JsonProperty("creator-id", required = true)
    @JacksonXmlProperty(localName = "creator-id")
    override val creatorId: Int, // 13
    @JsonProperty("score", required = true)
    @JacksonXmlProperty(localName = "score")
    override val score: Int, // 3
    @JacksonXmlProperty(localName = "do-not-bump-post")
    override val isBumped: Boolean, // false
    @JacksonXmlProperty(localName = "is-deleted")
    override val isDeleted: Boolean, // false
    @JacksonXmlProperty(localName = "is-sticky")
    override val isSticky: Boolean, // false
    @JacksonXmlProperty(localName = "updated-at")
    private val rawUpdationTime: String?, // 2005-05-25T16:31:50.000-04:00
    @JacksonXmlProperty(localName = "updater-id")
    override val updaterId: Int // 13
): DanbooruComment, Serializable {
    override val creationTime = time(rawCreationTime)
    override val updationTime = rawUpdationTime?.let(::time)
}

data class JsonDanbooruComment(
    @JsonProperty("id", required = true)
    override val commentId: Int, // 1
    @JsonProperty("post_id", required = true)
    override val postId: Int, // 162
    @JsonProperty("body", required = true)
    override val text: String, // Yoshiharu Makita - Radical Temptation[巻田佳春] RADICAL☆てんぷて～しょん
    @JsonProperty("created_at", required = true)
    private val rawCreationTime: String, // 2005-05-25T16:31:50.000-04:00
    @JsonProperty("creator_id", required = true)
    override val creatorId: Int, // 13
    @JsonProperty("score", required = true)
    override val score: Int, // 3
    @JsonProperty("do_not_bump_post")
    override val isBumped: Boolean, // false
    @JsonProperty("is_deleted")
    override val isDeleted: Boolean, // false
    @JsonProperty("is_sticky")
    override val isSticky: Boolean, // false
    @JsonProperty("updated_at")
    private val rawUpdationTime: String?, // 2005-05-25T16:31:50.000-04:00
    @JsonProperty("updater_id")
    override val updaterId: Int // 13
): DanbooruComment, Serializable {
    override val creationTime = time(rawCreationTime)
    override val updationTime = rawUpdationTime?.let(::time)
}
