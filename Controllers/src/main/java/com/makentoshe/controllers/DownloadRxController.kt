package com.makentoshe.controllers

import io.reactivex.subjects.Subject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Controller which uses coroutines for downloading a data.
 *
 * @param observable an observable instance for handling downloaded data to the subscribers.
 * @param coroutineScope for perform download into another thread.
 */
abstract class DownloadRxController<P, A>(
    observable: Subject<DownloadResult<P>>,
    protected val coroutineScope: CoroutineScope
) : SimpleRxController<DownloadResult<P>, A>(observable) {

    /**
     * Makes download using [param]. The main download process occurs in [performDownload] method.
     * This method is just a wrapper for handling exceptions.
     */
    override fun action(param: A) {
        coroutineScope.launch {
            try {
                observable.onNext(DownloadResult(performDownload(param)))
            } catch (e: Exception) {
                observable.onNext(DownloadResult(e))
            }
        }
    }

    /**
     * Main method for performing download. If downloading unsuccessful or something unwanted
     * we can throw an [Exception] and the observable will return [DownloadResult] with the thrown
     * exception.
     *
     * @param param parameter for downloading.
     * @return downloading result
     */
    abstract fun performDownload(param: A): P
}