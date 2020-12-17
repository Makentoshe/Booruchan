package com.makentoshe.booruchan.application.android.database.dao

import androidx.room.*
import com.makentoshe.booruchan.application.android.database.record.PreviewContentRecord

@Dao
interface PreviewContentDao: ContentDao<PreviewContentRecord> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insert(content: PreviewContentRecord)

    @Query("SELECT * FROM PreviewContentRecord")
    override fun getAll(): List<PreviewContentRecord>

    @Query("SELECT COUNT(*) FROM PreviewContentRecord")
    override fun count(): Long

    @Query("SELECT * FROM PreviewContentRecord WHERE history IN (SELECT history FROM PreviewContentRecord ORDER BY history ASC LIMIT :n)")
    override fun getLast(n: Int): List<PreviewContentRecord>

    @Query("DELETE FROM PreviewContentRecord WHERE history IN (SELECT history FROM PreviewContentRecord ORDER BY history ASC LIMIT :n)")
    override fun dropLast(n: Int)

    @Delete
    override fun delete(record: PreviewContentRecord)

    @Query("DELETE FROM PreviewContentRecord")
    override fun clear()
}