package com.makentoshe.booruchan.application.android

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PostsDeserializeWrapper::class], version = 1)
abstract class BooruchanDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
}