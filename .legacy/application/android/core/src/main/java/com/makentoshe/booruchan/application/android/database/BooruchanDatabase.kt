package com.makentoshe.booruchan.application.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.makentoshe.booruchan.application.android.database.dao.PreviewContentDao
import com.makentoshe.booruchan.application.android.database.dao.SampleContentDao
import com.makentoshe.booruchan.application.android.database.record.PreviewContentRecord
import com.makentoshe.booruchan.application.android.database.record.SampleContentRecord

@Database(
    entities = [PostsDeserializeWrapper::class, PreviewContentRecord::class, SampleContentRecord::class],
    version = 1
)
abstract class BooruchanDatabase : RoomDatabase() {

    abstract fun postsDao(): PostsDao

    abstract fun previewContentDao(): PreviewContentDao

    abstract fun sampleContentDao() : SampleContentDao
}