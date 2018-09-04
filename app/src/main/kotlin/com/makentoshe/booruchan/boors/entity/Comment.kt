package com.makentoshe.booruchan.boors.entity

import java.io.Serializable

abstract class Comment: Serializable {

    lateinit var post_id: String

    lateinit var id: String

    lateinit var creator: String

    lateinit var creator_id: String

    lateinit var body: String

    lateinit var created_at: String

    abstract fun fill(attributes: Map<String, String>)

}
