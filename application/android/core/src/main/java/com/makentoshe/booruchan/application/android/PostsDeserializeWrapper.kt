package com.makentoshe.booruchan.application.android

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PostsDeserializes")
data class PostsDeserializeWrapper(
    val filterUrl: String,
    @PrimaryKey
    val rawValue: String
)
