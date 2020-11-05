package com.makentoshe.booruchan.application.android

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDao {

    @Insert
    fun insert(post: PostWrapper)

    @Query("SELECT * FROM Posts")
    fun getAll(): List<PostWrapper>
}