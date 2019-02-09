package com.makentoshe.booruchan.postpreview.model

import com.makentoshe.controllers.DownloadRxController
import com.makentoshe.repository.Repository
import io.reactivex.subjects.ReplaySubject
import kotlinx.coroutines.CoroutineScope

/**
 * Class performs previews downloading.
 */
class PreviewsDownloadController(
    coroutineScope: CoroutineScope,
    private val repository: Repository<String, ByteArray>,
    cacheCapacity: Int
): DownloadRxController<Pair<String, ByteArray>, String>(ReplaySubject.create(cacheCapacity), coroutineScope) {
    override fun performDownload(param: String): Pair<String, ByteArray> {
        return Pair(param, repository.get(param)!!)
    }
}