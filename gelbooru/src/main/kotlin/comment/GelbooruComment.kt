package comment

import Time
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import post.PostId
import time

interface GelbooruComment : CommentId, PostId {
    val creatorId: Int
    val text: String
    val creationTime: Time
    val creator: String
}

data class XmlGelbooruComment(
    @JsonProperty("id", required = true)
    @JacksonXmlProperty(localName = "id")
    override val commentId: Int,
    @JsonProperty("post_id", required = true)
    @JacksonXmlProperty(localName = "post_id")
    override val postId: Int,
    @JsonProperty("body", required = true)
    @JacksonXmlProperty(localName = "body")
    override val text: String,
    @JsonProperty("created_at", required = true)
    @JacksonXmlProperty(localName = "created_at")
    private val rawCreationTime: String,
    @JsonProperty("creator_id")
    @JacksonXmlProperty(localName = "creator_id")
    override val creatorId: Int,
    @JsonProperty("creator")
    @JacksonXmlProperty(localName = "creator")
    override val creator: String
): GelbooruComment {

    @JsonIgnore
    override val creationTime = time(rawCreationTime)
}
