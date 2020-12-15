package com.makentoshe.booruchan.application.android.database

import androidx.room.*
import com.makentoshe.booruchan.application.android.database.record.PreviewContentRecord

@Dao
interface PreviewContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(content: PreviewContentRecord)

    @Query("SELECT * FROM PreviewContentRecord")
    fun getAll(): List<PreviewContentRecord>

    @Query("SELECT COUNT(*) FROM PreviewContentRecord")
    fun count(): Long

    @Query("SELECT * FROM PreviewContentRecord WHERE history IN (SELECT history FROM PreviewContentRecord ORDER BY history ASC LIMIT :n)")
    fun getLast(n: Int): List<PreviewContentRecord>

    @Query("DELETE FROM PreviewContentRecord WHERE history IN (SELECT history FROM PreviewContentRecord ORDER BY history ASC LIMIT :n)")
    fun dropLast(n: Int)

    @Delete
    fun delete(record: PreviewContentRecord)

    @Query("DELETE FROM PreviewContentRecord")
    fun clear()
}