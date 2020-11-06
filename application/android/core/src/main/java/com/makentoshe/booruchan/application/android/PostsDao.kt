package com.makentoshe.booruchan.application.android

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(postsDeserialize: PostsDeserializeWrapper)

    @Query("SELECT * FROM PostsDeserializes")
    fun getAll(): List<PostsDeserializeWrapper>

    @Query("SELECT * FROM PostsDeserializes WHERE filterUrl = :filterUrl")
    fun getByFilterUrl(filterUrl: String): PostsDeserializeWrapper?

    @Query("DELETE FROM PostsDeserializes")
    fun clear()
}