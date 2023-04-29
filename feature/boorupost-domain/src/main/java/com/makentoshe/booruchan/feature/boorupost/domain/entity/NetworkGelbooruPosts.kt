package com.makentoshe.booruchan.feature.boorupost.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkGelbooruPosts(
    @SerialName("@attributes")
    val attributes: Attributes,
    @SerialName("post")
    val posts: List<NetworkGelbooruPost>
)

@Serializable
data class Attributes(
    @SerialName("count")
    val count: Int,
    @SerialName("limit")
    val limit: Int,
    @SerialName("offset")
    val offset: Int
)

@Serializable
data class NetworkGelbooruPost(
    @SerialName("id")
    override val id: Int,
    @SerialName("preview_url")
    override val previewImageUrl: String,

    @SerialName("change")
    val change: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("creator_id")
    val creatorId: Int,
    @SerialName("directory")
    val directory: String,
    @SerialName("file_url")
    val fileUrl: String,
    @SerialName("has_children")
    val hasChildren: String,
    @SerialName("has_comments")
    val hasComments: String,
    @SerialName("has_notes")
    val hasNotes: String,
    @SerialName("height")
    val height: Int,
    @SerialName("image")
    val image: String,
    @SerialName("md5")
    val md5: String,
    @SerialName("owner")
    val owner: String,
    @SerialName("parent_id")
    val parentId: Int,
    @SerialName("post_locked")
    val postLocked: Int,
    @SerialName("preview_height")
    val previewHeight: Int,
    @SerialName("preview_width")
    val previewWidth: Int,
    @SerialName("rating")
    val rating: String,
    @SerialName("sample")
    val sample: Int,
    @SerialName("sample_height")
    val sampleHeight: Int,
    @SerialName("sample_url")
    val sampleUrl: String,
    @SerialName("sample_width")
    val sampleWidth: Int,
    @SerialName("score")
    val score: Int,
    @SerialName("source")
    val source: String,
    @SerialName("status")
    val status: String,
    @SerialName("tags")
    val tags: String,
    @SerialName("title")
    val title: String,
    @SerialName("width")
    val width: Int
): NetworkBooruPost
