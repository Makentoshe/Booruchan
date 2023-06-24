package com.makentoshe.booruchan.application.android.database

import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO maybe serialize and deserialize posts with GSON?
@Entity(tableName = "PostsDeserializes")
data class PostsDeserializeWrapper(
    val filterUrl: String,
    @PrimaryKey
    val rawValue: String
)
