package com.makentoshe.booruchan.api

import java.io.Serializable

interface Post : Serializable {
    val raw: Map<String, String>
    val id: Long
    val score: Int
    val previewUrl: String
    val previewWidth: Int
    val previewHeight: Int
    val sampleUrl: String
    val fileUrl: String
    val creatorId: Long
    val rating: Rating
    val tags: Array<Tag>
    val source: String

    enum class Rating {
        SAFE, QUESTIONABLE, EXPLICIT, UNSPECIFIED;

        companion object {
            fun parseRating(str: String?): Post.Rating {
                return when (str) {
                    "s" -> Post.Rating.SAFE
                    "q" -> Post.Rating.QUESTIONABLE
                    "e" -> Post.Rating.EXPLICIT
                    else -> Post.Rating.UNSPECIFIED
                }
            }
        }
    }

    companion object {
        fun create(id: Long, sampleUrl: String = "") = object : Post {
            override val raw: Map<String, String>
                get() = emptyMap()
            override val id: Long
                get() = id
            override val score: Int
                get() = -1
            override val previewUrl: String
                get() = ""
            override val sampleUrl: String
                get() = sampleUrl
            override val fileUrl: String
                get() = ""
            override val creatorId: Long
                get() = -1L
            override val rating: Rating
                get() = Rating.UNSPECIFIED
            override val tags: Array<Tag>
                get() = arrayOf()
            override val previewWidth: Int
                get() = -1
            override val previewHeight: Int
                get() = -1
            override val source: String
                get() = ""
        }
    }
}