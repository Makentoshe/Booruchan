package com.makentoshe.booruimageview

import com.makentoshe.api.repository.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Performs downloading from repository
 */
abstract class Download<K, V> {

    protected abstract val repository: Repository<K, V>

    /** Performs downloading from repository */
    open fun execute(key: K, action: (V?, Throwable?) -> Unit) = Single.just(key)
        .observeOn(Schedulers.io())
        .map(repository::get)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(action)
}