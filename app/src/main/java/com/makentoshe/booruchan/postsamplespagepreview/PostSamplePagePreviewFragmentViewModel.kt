package com.makentoshe.booruchan.postsamplespagepreview

import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.ImageDownloadController
import com.makentoshe.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class PostSamplePagePreviewFragmentViewModel(
    samplesRepository: Repository<String, ByteArray>,
    val position: Int
) : ViewModel(), CoroutineScope {

    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    val sampleDownloadController = ImageDownloadController(this, samplesRepository)

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}
