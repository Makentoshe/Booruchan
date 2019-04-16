package com.makentoshe.booruchan.api.component.comment

interface Comment {
    val raw: Map<String, String>
    val postId: Long
    val body: String
    val id: Long
    val createdAt: String
    val creator: String
    val creatorId: Long
}