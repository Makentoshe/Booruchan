package com.makentoshe.booruchan.application.android.database.dao

import androidx.room.*
import com.makentoshe.booruchan.application.android.database.record.SampleContentRecord

@Dao
interface SampleContentDao: ContentDao<SampleContentRecord> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insert(content: SampleContentRecord)

    @Query("SELECT * FROM SampleContentRecord")
    override fun getAll(): List<SampleContentRecord>

    @Query("SELECT COUNT(*) FROM SampleContentRecord")
    override fun count(): Long

    @Query("SELECT * FROM SampleContentRecord WHERE history IN (SELECT history FROM SampleContentRecord ORDER BY history ASC LIMIT :n)")
    override fun getLast(n: Int): List<SampleContentRecord>

    @Query("DELETE FROM SampleContentRecord WHERE history IN (SELECT history FROM SampleContentRecord ORDER BY history ASC LIMIT :n)")
    override fun dropLast(n: Int)

    @Delete
    override fun delete(record: SampleContentRecord)

    @Query("DELETE FROM SampleContentRecord")
    override fun clear()
}
