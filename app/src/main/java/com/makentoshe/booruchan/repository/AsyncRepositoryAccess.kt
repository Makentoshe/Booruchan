package com.makentoshe.booruchan.repository

import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AsyncRepositoryAccess<K, V>(
    private val repository: Repository<K, V>,
    private val cContext: CoroutineContext = GlobalScope.coroutineContext
) {

    private var observable = BehaviorSubject.create<Pair<V?, Throwable?>>()

    fun request(key: K) {
        GlobalScope.launch(cContext) {
            try {
                observable.onNext(Pair(repository.get(key)!!, null))
            } catch (e: Exception) {
                observable.onNext(Pair(null, e))
            }
        }
    }

    fun onComplete(action: (V) -> Unit): Disposable {
        return observable.subscribe {
            if (it.first != null) action(it.first!!)
        }
    }

    fun onError(action: (Throwable) -> Unit): Disposable {
        return observable.subscribe {
            if (it.second != null) action(it.second!!)
        }
    }
}