package com.makentoshe.booruchan.application.android

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makentoshe.booruchan.core.post.*
import com.makentoshe.booruchan.core.time

@Entity(tableName = "Posts")
data class PostWrapper(
    val filterUrl: String,
    @PrimaryKey
    val postId: Int,
    val htwRatio: Float,
    val source: String?,
    val rawCreationTime: String,
    val rawScore: Int,
    val rawUpScore: Int?,
    val rawDownScore: Int?,
    val rawRating: String,
    val fullImageUrl: String,
    val fullImageHeight: Int?,
    val fullImageWidth: Int?,
    val sampleImageUrl: String,
    val sampleImageHeight: Int?,
    val sampleImageWidth: Int?,
    val previewImageUrl: String,
    val previewImageHeight: Int?,
    val previewImageWidth: Int?,
    val tags: String
) {

    constructor(filterUrl: String, post: Post) : this(
        filterUrl, post.postId, post.htwRatio, post.source,
        post.creationTime.raw,
        post.score.score, post.score.upScore, post.score.downScore,
        post.rating.name,
        post.fullImage.url, post.fullImage.height, post.fullImage.width,
        post.sampleImage.url, post.sampleImage.height, post.sampleImage.width,
        post.previewImage.url, post.previewImage.height, post.previewImage.width,
        post.tags.tags.joinToString(" ") { it.text }
    )

    fun toPost() = object : Post {
        override val postId = this@PostWrapper.postId
        override val source = this@PostWrapper.source
        override val htwRatio = this@PostWrapper.htwRatio
        override val creationTime = time(rawCreationTime)
        override val rating = Rating.valueOf(rawRating)
        override val fullImage = fullImage(fullImageUrl, fullImageHeight, fullImageWidth)
        override val sampleImage = sampleImage(sampleImageUrl, sampleImageHeight, sampleImageWidth)
        override val previewImage = previewImage(previewImageUrl, previewImageHeight, previewImageWidth)
        override val score =
            if (rawUpScore != null && rawDownScore != null) score(rawUpScore, rawDownScore) else score(rawScore)
        override val tags = tagsFromString(this@PostWrapper.tags.split(" ").toSet())
    }
}