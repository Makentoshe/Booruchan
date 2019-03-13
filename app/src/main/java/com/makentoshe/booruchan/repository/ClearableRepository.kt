package com.makentoshe.booruchan.repository

interface ClearableRepository<K, V>: Repository<K, V> {
    fun clear()
}