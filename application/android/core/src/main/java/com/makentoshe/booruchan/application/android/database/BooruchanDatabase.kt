package com.makentoshe.booruchan.application.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.makentoshe.booruchan.application.android.database.record.PreviewContentRecord

@Database(entities = [PostsDeserializeWrapper::class, PreviewContentRecord::class], version = 2)
abstract class BooruchanDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao

    abstract fun previewContentDao(): PreviewContentDao
}