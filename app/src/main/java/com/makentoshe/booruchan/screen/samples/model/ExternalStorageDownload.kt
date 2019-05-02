package com.makentoshe.booruchan.screen.samples.model

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.StreamDownloadController
import com.makentoshe.booruchan.repository.stream.StreamDownloadRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.CoroutineContext

class ExternalStorageDownload(
    private val booru: Booru,
    private val post: Post,
    private val ccontext: CoroutineContext = GlobalScope.coroutineContext
) {

    fun start(context: Context, result: (DownloadedData?, Throwable?) -> Unit) {
        startStreamDownload(context, result)
    }

    private fun startStreamDownload(context: Context, result: (DownloadedData?, Throwable?) -> Unit) {
        //add listeners
        val listener = StreamDownloadController.create().apply {
            onPartReceived { length, _, progress ->
                NotificationProcess(post).start(context) {
                    setProgress(100, (progress * 100).toInt(), false)
                    setContentText("${length * progress}/$length bytes")
                }
            }
        }
        //init streaming download
        GlobalScope.launch(ccontext) {
            try {
                val byteArray = StreamDownloadRepository(listener, booru).get(post.fileUrl)
                Handler(Looper.getMainLooper()).post { result(byteArray.toDownloadData(post, booru), null) }
            } catch (e: Exception) {
                NotificationUnsuccessProcess(
                    e,
                    post.id.toInt(),
                    NotificationProcess(post)
                ).start(context)
            }
        }
    }

    private fun ByteArray.toDownloadData(post: Post, booru: Booru): DownloadedData {
        val title = post.id.toString()
        val extension = File(post.fileUrl).extension
        return DownloadedData(this, title, extension, booru.title)
    }
}