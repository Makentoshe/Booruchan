package com.makentoshe.booruchan.common.api.entity

import java.io.Serializable

abstract class Comment(val raw: Map<String, Any>): Serializable {
    val post_id: Int by raw
    val id: Int by raw
    val creator: String by raw
    val creator_id: Int by raw
    val body: String by raw
    val created_at: String by raw
}
