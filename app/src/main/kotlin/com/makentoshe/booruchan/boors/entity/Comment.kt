package com.makentoshe.booruchan.boors.entity

import java.io.Serializable

abstract class Comment: Serializable {

    var postId: Int = -1

    var id: Int = -1

    lateinit var creator: String

    var creatorId: Int = -1

    lateinit var body: String

    lateinit var createdAt: String

    lateinit var raw: Map<String, String>

    fun fill(attributes: Map<String, String>) {
        raw = attributes
        //create Entry
        val entrySet = attributes.entries
        //for each attribute
        for ((key, value) in entrySet) {
            when (key) {
                "id" -> {
                    id = value.toInt()
                }
                "creator" -> {
                    creator = value
                }
                "creator_id" -> {
                    creatorId = value.toInt()
                }
                "body" -> {
                    body = value
                }
                "post_id" -> {
                    postId = value.toInt()
                }
                "created_at" -> {
                    createdAt = value
                }
            }
        }
    }

}
