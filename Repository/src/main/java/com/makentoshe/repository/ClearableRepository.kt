package com.makentoshe.repository

interface ClearableRepository<K, V>: Repository<K, V> {
    fun clear()
}