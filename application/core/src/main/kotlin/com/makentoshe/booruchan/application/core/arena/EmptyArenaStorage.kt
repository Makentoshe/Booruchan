package com.makentoshe.booruchan.application.core.arena

/** Special always empty arena cache */
class EmptyArenaStorage<K, V>: ArenaStorage<K, V> {

    override fun fetch(key: K): Result<V> {
        return Result.failure(ArenaStorageException("EmptyArenaStorage call"))
    }

    override fun carry(key: K, value: V) {

    }
}