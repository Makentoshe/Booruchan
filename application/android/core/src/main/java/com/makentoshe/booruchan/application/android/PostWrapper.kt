package com.makentoshe.booruchan.application.android

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Posts")
class PostWrapper(val booruTitle: String, val postId: Int) {

    @PrimaryKey
    var primaryKey = "$booruTitle-$postId"
}