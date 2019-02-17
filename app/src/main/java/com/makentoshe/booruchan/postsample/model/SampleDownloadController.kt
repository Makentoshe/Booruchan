package com.makentoshe.booruchan.postsample.model

import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.ByteArrayDownloadRxController
import com.makentoshe.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File

class SampleDownloadController(
    coroutineScope: CoroutineScope,
    private val repository: Repository<Post, ByteArray>
) : ByteArrayDownloadRxController<Post>(coroutineScope, repository) {
    override fun action(param: Post) {
        coroutineScope.launch {
            try {
                if (File(param.sampleUrl).extension == "webm") {
                    observable.onNext(com.makentoshe.controllers.DownloadResult(param.sampleUrl.toByteArray()))
                } else {
                    observable.onNext(com.makentoshe.controllers.DownloadResult(repository.get(param)!!))
                }
            } catch (e: Exception) {
                observable.onNext(com.makentoshe.controllers.DownloadResult(exception = e))
            }
        }
    }
}