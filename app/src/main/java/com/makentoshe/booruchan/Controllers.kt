package com.makentoshe.booruchan

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.controllers.DownloadRxController
import com.makentoshe.controllers.SimpleRxController
import com.makentoshe.repository.Repository
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import java.io.Serializable

/**
 * Class for downloading a [Posts] in another thread.
 *
 * @param coroutineScope for perform download into another thread.
 * @param repository contains posts.
 */
open class PostsDownloadRxController(
    coroutineScope: CoroutineScope,
    private val repository: Repository<Booru.PostRequest, Posts>
) : DownloadRxController<Posts, Booru.PostRequest>(BehaviorSubject.create(), coroutineScope) {

    /**
     * Performs download using [param] and returns [Posts]
     *
     * @param param page index.
     * @return a [Posts] instance which is associated with the [param].
     */
    override fun performDownload(param: Booru.PostRequest): Posts {
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
) : DownloadRxController<ByteArray, P>(BehaviorSubject.create(), coroutineScope) {

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

/**
 * Just send a String to the subscribers.
 */
class StringRxController : SimpleRxController<String, String>(BehaviorSubject.create()) {
    override fun action(param: String) = observable.onNext(param)
}


/**
 * Just send an Unit to the subscribers.
 */
class UnitRxController : SimpleRxController<Unit, Unit>(BehaviorSubject.create()), Serializable {
    override fun action(param: Unit) = observable.onNext(Unit)
}