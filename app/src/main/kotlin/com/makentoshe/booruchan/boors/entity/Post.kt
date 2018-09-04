package com.makentoshe.booruchan.boors.entity

import java.io.Serializable

abstract class Post: Serializable {

    enum class Rating {
        SAFE, QUESTIONABLE, EXPLICIT, WTF
    }

    lateinit var sampleUrl: String

    lateinit var tags: Array<String>

    lateinit var previewUrl: String

    lateinit var fileUrl: String

    var id: Int = -1

    lateinit var createdAt: String

    var creatorId: Int = -1

    lateinit var rating: Rating

    var score: Int = 0

    lateinit var raw: Map<String, String>

    var hasComments: Boolean = false

    open fun fill(attributes: Map<String, String>) {
        raw = attributes
        //create Entry
        val entrySet = attributes.entries
        //for each attribute
        for ((key, value) in entrySet) {
            when (key) {
                "id" -> {
                    id = value.toInt()
                }
                "rating" -> {
                    rating = parseRating(value)
                }
                "score" -> {
                    score = value.toInt()
                }
                "preview_url" -> {
                    previewUrl = value
                }
                "tags" -> {
                    tags = value.split(" ").toTypedArray()
                }
                "sample_url" -> {
                    sampleUrl = value
                }
                "file_url" -> {
                    fileUrl = value
                }
                "creator_id" -> {
                    creatorId = value.toInt()
                }
                "has_comments" -> {
                    hasComments = value.toBoolean()
                }
                "created_at" -> {
                    createdAt = value
                }
            }
        }
    }

    private fun parseRating(str: String): Rating {
        return when (str) {
            "s" -> Rating.SAFE
            "q" -> Rating.QUESTIONABLE
            "e" -> Rating.EXPLICIT
            else -> Rating.WTF
        }
    }

}
