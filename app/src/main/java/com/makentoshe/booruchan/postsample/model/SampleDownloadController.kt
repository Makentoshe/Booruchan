package com.makentoshe.booruchan.postsample.model

import com.makentoshe.booruchan.ByteArrayDownloadRxController
import com.makentoshe.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File

class SampleDownloadController(
    coroutineScope: CoroutineScope,
    private val repository: Repository<String, ByteArray>
) : ByteArrayDownloadRxController<String>(coroutineScope, repository) {
    override fun action(param: String) {
        coroutineScope.launch {
            try {
                if (File(param).extension == "webm") {
                    observable.onNext(com.makentoshe.controllers.DownloadResult(param.toByteArray()))
                } else {
                    observable.onNext(com.makentoshe.controllers.DownloadResult(repository.get(param)!!))
                }
            } catch (e: Exception) {
                observable.onNext(com.makentoshe.controllers.DownloadResult(exception = e))
            }
        }
    }
}