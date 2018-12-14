package com.makentoshe.repository

interface Repository<K, V> {
    fun get(key: K): V?
}