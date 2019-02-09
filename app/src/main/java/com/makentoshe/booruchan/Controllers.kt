package com.makentoshe.booruchan

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import com.makentoshe.controllers.DownloadRxController
import com.makentoshe.controllers.SimpleRxController
import com.makentoshe.repository.ImageRepository
import com.makentoshe.repository.Repository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.Serializable

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
 * @param repository contains the posts.
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