package com.makentoshe.booruchan.screen.samples.model

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.model.StreamDownload
import com.makentoshe.booruchan.screen.settings.AppSettings
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.CoroutineContext

class DownloadProcess(
    private val booru: Booru,
    private val post: Post,
    private val ccontext: CoroutineContext = GlobalScope.coroutineContext
) {

    fun start(context: Context, result: (DownloadedData?, Throwable?) -> Unit) {
        if (AppSettings.getStreamingDownload(context)) {
            startStreamDownload(context, result)
        } else {
            startDefaultDownload(result)
        }
    }

    private fun startDefaultDownload(result: (DownloadedData?, Throwable?) -> Unit) {
        //start a new coroutine for loading an image
        GlobalScope.launch(ccontext) {
            try {
                val response = booru.getCustom().request(post.fileUrl)
                //perform loading and reading bytes
                val bytes = response.stream.readBytes()
                //box all data into the data class
                val data = bytes.toDownloadData(post, booru)
                //return to main thread
                Handler(Looper.getMainLooper()).post { result(data, null) }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post { result(null, e) }
            }
        }
    }

    private fun startStreamDownload(context: Context, result: (DownloadedData?, Throwable?) -> Unit) {
        //init streaming download
        val streamDownload = StreamDownload(ccontext, booru, 1024 * 64)
        //add listeners
        streamDownload.addListener {
            onComplete {
                //return to main thread
                Handler(Looper.getMainLooper()).post { result(it.toDownloadData(post, booru), null) }
            }
            onPartReceived { length, _, progress ->
                NotificationProcess(post).start(context) {
                    setProgress(100, (progress * 100).toInt(), false)
                    setContentText("${length * progress}/$length bytes")
                }
            }
            onError {
                NotificationUnsuccessProcess(it, post.id.toInt(), NotificationProcess(post)).start(context)
            }
        }
        //start download
        streamDownload.download(post.fileUrl)
    }

    private fun ByteArray.toDownloadData(post: Post, booru: Booru): DownloadedData {
        val title = post.id.toString()
        val extension = File(post.fileUrl).extension
        return DownloadedData(this, title, extension, booru.title)
    }
}