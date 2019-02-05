package com.makentoshe.booruchan.postsamples.model

import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.DownloadDoubleController
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.repository.ImageRepository
import kotlinx.coroutines.CoroutineScope

/**
 * Class for performing file download using [Post] instance and return a byte array as a result.
 */
class FileImageDownloadController(
    coroutineScope: CoroutineScope, private val repository: ImageRepository
) : DownloadDoubleController<Post, ByteArray>(coroutineScope) {

    /**
     * Perform downloading the sample image.
     */
    override fun performDownload(request: DownloadResult<Post>): ByteArray {
        return repository.get(request.data!!.fileUrl)!!
    }
}