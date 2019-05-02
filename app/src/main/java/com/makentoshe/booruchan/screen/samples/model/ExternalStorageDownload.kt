package com.makentoshe.booruchan.screen.samples.model

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.StreamDownloadController
import com.makentoshe.booruchan.repository.stream.StreamDownloadRepository
import com.makentoshe.booruchan.repository.stream.StreamRepositoryFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.io.File
import kotlin.coroutines.CoroutineContext

/**
 * Performs a file downloading associates with the [post] from [booru]
 */
class ExternalStorageDownload(
    private val booru: Booru,
    private val post: Post,
    private val ccontext: CoroutineContext = GlobalScope.coroutineContext
) : KoinComponent {

    private val streamListener by inject<StreamDownloadController>()

    private val streamRepositoryFactory by inject<StreamRepositoryFactory> { parametersOf(booru, streamListener) }

    private val notificationProcess = NotificationProcess(post)

    fun start(context: Context, result: (DownloadedData?, Throwable?) -> Unit) {
        startStreamDownload(context, result)
    }

    private fun startStreamDownload(context: Context, result: (DownloadedData?, Throwable?) -> Unit) {
        setListeners(context)
        val repository = streamRepositoryFactory.buildFilesRepository()
        //init streaming download
        GlobalScope.launch(ccontext) {
            try {
                val byteArray = repository.get(post)!!
                Handler(Looper.getMainLooper()).post { result(byteArray.toDownloadData(post, booru), null) }
            } catch (e: Exception) {
                NotificationUnsuccessProcess(e, post.id.toInt(), notificationProcess).start(context)
            }
        }
    }

    private fun setListeners(context: Context) {
        //add listeners
        streamListener.onPartReceived { length, _, progress ->
            notificationProcess.start(context) {
                setProgress(100, (progress * 100).toInt(), false)
                setContentText("${length * progress}/$length bytes")
            }
        }
    }

    private fun ByteArray.toDownloadData(post: Post, booru: Booru): DownloadedData {
        val title = post.id.toString()
        val extension = File(post.fileUrl).extension
        return DownloadedData(this, title, extension, booru.title)
    }
}