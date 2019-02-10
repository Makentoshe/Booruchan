package com.makentoshe.repository

import java.io.Serializable

interface Repository<K, V>: Serializable {
    fun get(key: K): V?
}