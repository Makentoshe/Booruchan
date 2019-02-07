package com.makentoshe.booruchan.postpreview.model

import com.makentoshe.booruchan.Controller
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.repository.ImageRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.ReplaySubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Class for performing preview image download.
 */
class PreviewImageDownloadController(
    private val previewsRepository: ImageRepository
) : Controller<DownloadResult<Pair<String, ByteArray>>> {
    private val observable = ReplaySubject.create<DownloadResult<Pair<String, ByteArray>>>()
    private val disposables = CompositeDisposable()

    override fun subscribe(action: (DownloadResult<Pair<String, ByteArray>>) -> Unit) {
        disposables.add(observable.subscribe(action))
    }

    //TODO Memory leak in coroutines
    //PreviewImageDownloadController instance can't be GC
    fun action(url: String, cScope: CoroutineScope) = cScope.launch {
        try {
            observable.onNext(DownloadResult(Pair(url, previewsRepository.get(url)!!)))
        } catch (e: Exception) {
            observable.onNext(DownloadResult(exception = e))
        }
    }

    override fun clear() {
        disposables.clear()
        observable.cleanupBuffer()
    }
}