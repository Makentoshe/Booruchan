package com.makentoshe.booruchan

import com.makentoshe.booruapi.Posts
import com.makentoshe.controllers.DownloadRxController
import com.makentoshe.repository.Repository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.CoroutineScope

/**
 * Default controller interface.
 */
interface Controller<T> {
    fun subscribe(action: (T) -> Unit)

    fun clear()
}

/**
 * Any download will be wrapped in this class.
 */
data class DownloadResult<T>(val data: T? = null, val exception: Exception? = null)

class RequestPermissionController : Controller<String> {
    private val observable = PublishSubject.create<String>()
    private val disposables = CompositeDisposable()

    override fun subscribe(action: (String) -> Unit) {
        disposables.add(observable.subscribe(action))
    }

    fun action(permission: String) = observable.onNext(permission)

    override fun clear() = disposables.clear()
}

/**
 * Class for downloading a [Posts] in another thread.
 *
 * @param coroutineScope for perform download into another thread.
 * @param repository contains posts.
 */
open class PostsDownloadRxController(
    coroutineScope: CoroutineScope,
    private val repository: Repository<Int, Posts>
): DownloadRxController<Posts, Int>(BehaviorSubject.create(), coroutineScope) {

    /**
     * Performs download using [param] and returns [Posts]
     *
     * @param param page index.
     * @return a [Posts] instance which is associated with the [param].
     */
    override fun performDownload(param: Int): Posts {
        return repository.get(param)!!
    }
}

/**
 * Class for downloading a [ByteArray] in another thread.
 *
 * @param coroutineScope for perform download into another thread.
 * @param repository contains byte arrays.
 */
open class ByteArrayDownloadRxController<P>(
    coroutineScope: CoroutineScope,
    private val repository: Repository<P, ByteArray>
): DownloadRxController<ByteArray, P>(BehaviorSubject.create(), coroutineScope) {

    /**
     * Performs download using a param and returns [ByteArray]
     *
     * @param param for the downloading.
     * @return a [ByteArray] instance which is associated with the param.
     */
    override fun performDownload(param: P): ByteArray {
        return repository.get(param)!!
    }
}