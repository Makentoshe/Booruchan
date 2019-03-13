package com.makentoshe.booruchan.api

import java.io.Serializable

interface Post : Serializable {
    val raw: Map<String, String>
    val id: Long
    val score: Int
    val previewUrl: String
    val sampleUrl: String
    val fileUrl: String
    val creatorId: Long
    val rating: Rating
    val tags: Array<Tag>

    enum class Rating {
        SAFE, QUESTIONABLE, EXPLICIT, UNSPECIFIED
    }
}