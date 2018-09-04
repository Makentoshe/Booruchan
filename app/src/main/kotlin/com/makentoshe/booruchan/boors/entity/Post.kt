package com.makentoshe.booruchan.boors.entity

import java.io.Serializable

abstract class Post: Serializable{

    lateinit var sample_url: String

    lateinit var tags: Array<String>

    lateinit var preview_url: String

    lateinit var file_url: String

    lateinit var id: String

    lateinit var created_at: String

    lateinit var creator_id: String

    lateinit var rating: String

    lateinit var score: String

    lateinit var raw: Map<String, String>

    lateinit var has_comments: String

    abstract fun fill(attributes: Map<String, String>)

}
