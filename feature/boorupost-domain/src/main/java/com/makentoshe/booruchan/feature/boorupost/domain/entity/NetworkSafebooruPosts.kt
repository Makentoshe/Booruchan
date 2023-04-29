package com.makentoshe.booruchan.feature.boorupost.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias NetworkSafebooruPosts = ArrayList<NetworkSafebooruPost>

@Serializable
data class NetworkSafebooruPost(
    @SerialName("id")
    override val id: Int,
    @SerialName("change")
    val change: Int,
    @SerialName("directory")
    val directory: String,
    @SerialName("hash")
    val hash: String,
    @SerialName("height")
    val height: Int,
    @SerialName("image")
    val image: String,
    @SerialName("owner")
    val owner: String,
    @SerialName("parent_id")
    val parentId: Int,
    @SerialName("rating")
    val rating: String,
    @SerialName("sample")
    val sample: Boolean,
    @SerialName("sample_height")
    val sampleHeight: Int,
    @SerialName("sample_width")
    val sampleWidth: Int,
    @SerialName("score")
    val score: Int?,
    @SerialName("tags")
    val tags: String,
    @SerialName("width")
    val width: Int,
): NetworkBooruPost {
    override val previewImageUrl: String
        get() = "thumbnails/$directory/thumbnail_$image"

    override val previewImageHeight: Int
        get() = sampleHeight

    override val previewImageWidth: Int
        get() = sampleWidth
}
