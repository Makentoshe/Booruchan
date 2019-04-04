package com.makentoshe.booruchan.screen.samples.model

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import kotlin.coroutines.CoroutineContext

class DownloadProcess(
    private val booru: Booru,
    private val post: Post,
    private val ccontext: CoroutineContext = GlobalScope.coroutineContext
) {

    fun start(context: Context, result: (DownloadedData?, Throwable?) -> Unit) {
        //start a new coroutine for loading an image
        GlobalScope.launch(ccontext) {
            try {
                val response = booru.getCustom().request(post.fileUrl)
                //perform loading and reading bytes
                val bytes = response.stream.readBytes(response.length) {
                    //set download progress
                    NotificationProcess(post).start(context) {
                        setProgress(100, (it * 100).toInt(), false)
                    }
                }
                //box all data into the data class
                val data = bytes.toDownloadData(post, booru)
                //return to main thread
                Handler(Looper.getMainLooper()).post { result(data, null) }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post { result(null, e) }
            }
        }
    }

    /* progress from 0 to 1 */
    private fun InputStream.readBytes(contentLength: Long, onProgress: (Float) -> Unit): ByteArray {
        val bufferSize = 16384
        val buffer = ByteArrayOutputStream()

        var count = 0
        val data = ByteArray(bufferSize)
        var nRead = read(data, 0, bufferSize)
        count += nRead

        while (nRead != -1) {
            buffer.write(data, 0, nRead)
            nRead = read(data, 0, bufferSize)
            onProgress(count / contentLength.toFloat())
            count += nRead
        }

        return buffer.toByteArray()
    }

    private fun ByteArray.toDownloadData(post: Post, booru: Booru): DownloadedData {
        val title = post.id.toString()
        val extension = File(post.fileUrl).extension
        return DownloadedData(this, title, extension, booru.title)
    }
}