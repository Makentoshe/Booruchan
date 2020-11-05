package com.makentoshe.booruchan.application.android

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PostWrapper::class], version = 2)
abstract class BooruchanDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}