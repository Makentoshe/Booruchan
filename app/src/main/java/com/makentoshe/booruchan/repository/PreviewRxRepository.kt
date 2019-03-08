package com.makentoshe.booruchan.repository

import com.makentoshe.booruapi.Post
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PreviewRxRepository(
    private val repository: Repository<Post, ByteArray>,
    private val cContext: CoroutineContext
) : RxRepository<Post, ByteArray> {

    private val observable = BehaviorSubject.create<ByteArray>()

    override fun get(key: Post) = repository.get(key)

    override fun request(key: Post) {
        GlobalScope.launch(cContext) { observable.onNext(get(key)!!) }
    }

    override fun onComplete(action: (ByteArray) -> Unit): Disposable {
        return observable.subscribe {
            action(it)
        }
    }
}