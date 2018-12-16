package com.makentoshe.booruapi

import java.io.Serializable
import java.lang.StringBuilder

data class Post(
    @JvmField val raw: Map<String, String>
) : Serializable, BooruType {

    constructor(
        id: Int = -1,
        score: Int = 0,
        previewUrl: String = "",
        sampleUrl: String = "",
        fileUrl: String = "",
        creatorId: Int = -1,
        rating: Rating = Rating.UNSPECIFIED,
        tags: Array<Tag> = arrayOf()
    ) : this(HashMap<String, String>().apply {
        put("id", id.toString())
        put("score", score.toString())
        put("preview_url", previewUrl)
        put("sample_url", sampleUrl)
        put("file_url", fileUrl)
        put("creator_id", creatorId.toString())
        put("rating", rating.name.toLowerCase().first().toString())
        put("tags", StringBuilder("").apply {
            tags.forEach { append(it.name).append(" ") }
        }.toString())
    })

    enum class Rating {
        SAFE, QUESTIONABLE, EXPLICIT, UNSPECIFIED
    }

    @JvmField
    var id = -1

    @JvmField
    var score = 0

    lateinit var previewUrl: String

    lateinit var sampleUrl: String

    lateinit var fileUrl: String

    @JvmField
    var creatorId = -1

    var rating: Rating? = null

    lateinit var tags: Array<Tag>

    init {
        for (entry in raw.entries) {
            when (entry.key) {
                "id" -> {
                    id = entry.value.toInt()
                }
                "score" -> {
                    score = entry.value.toInt()
                }
                "preview_url" -> {
                    previewUrl = entry.value
                }
                "sample_url" -> {
                    sampleUrl = entry.value
                }
                "file_url" -> {
                    fileUrl = entry.value
                }
                "creator_id" -> {
                    creatorId = entry.value.toInt()
                }
                "rating" -> {
                    rating = parseRating(entry.value)
                }
                "tags" -> {
                    tags = parseTags(entry.value)
                }
            }
        }
    }

    private fun parseRating(str: String): Rating {
        return when (str) {
            "s" -> Rating.SAFE
            "q" -> Rating.QUESTIONABLE
            "e" -> Rating.EXPLICIT
            else -> Rating.UNSPECIFIED
        }
    }

    private fun parseTags(str: String): Array<Tag> {
        if (str.isBlank()) return arrayOf()
        val stags = str.split(" ").toTypedArray()
        return Array(stags.size) {
            val map = HashMap<String, String>()
            map["name"] = stags[it]
            return@Array Tag(map)
        }
    }
}