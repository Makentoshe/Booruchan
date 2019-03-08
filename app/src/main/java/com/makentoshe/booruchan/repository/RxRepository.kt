package com.makentoshe.booruchan.repository

import io.reactivex.disposables.Disposable

interface RxRepository<K, V> : Repository<K, V> {
    fun request(key: K)
    fun onComplete(action: (V) -> Unit): Disposable
}