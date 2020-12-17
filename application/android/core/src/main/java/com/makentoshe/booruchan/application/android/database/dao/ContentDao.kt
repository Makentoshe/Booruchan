package com.makentoshe.booruchan.application.android.database.dao

interface ContentDao<Record> {

    fun insert(content: Record)

    fun getAll(): List<Record>

    fun count(): Long

    fun getLast(n: Int): List<Record>

    fun dropLast(n: Int)

    fun delete(record: Record)

    fun clear()
}