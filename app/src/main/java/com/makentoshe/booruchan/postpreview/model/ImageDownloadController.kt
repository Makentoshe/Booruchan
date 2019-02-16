package com.makentoshe.booruchan.postpreview.model

import com.makentoshe.booruapi.Post
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
    private val repository: Repository<Post, ByteArray>
) : DownloadRxController<ByteArray, Post>(BehaviorSubject.create(), coroutineScope) {

    /**
     * Gets byte array from the repository.
     */
    override fun performDownload(param: Post): ByteArray {
        return repository.get(param)!!
    }
}