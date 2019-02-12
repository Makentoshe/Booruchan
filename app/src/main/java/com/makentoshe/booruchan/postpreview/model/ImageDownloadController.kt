package com.makentoshe.booruchan.postpreview.model

import com.makentoshe.controllers.DownloadRxController
import com.makentoshe.repository.Repository
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.ReplaySubject
import kotlinx.coroutines.CoroutineScope

/**
 * Class performs images get from repository in another thread using coroutines.
 */
open class ImageDownloadController(
    coroutineScope: CoroutineScope,
    private val repository: Repository<String, ByteArray>
) : DownloadRxController<ByteArray, String>(BehaviorSubject.create(), coroutineScope) {

    /**
     * Gets byte array from the repository.
     */
    override fun performDownload(param: String): ByteArray {
        return repository.get(param)!!
    }
}