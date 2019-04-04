package com.makentoshe.booruchan.screen.samples.model

import com.makentoshe.booruchan.api.Booru
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import kotlin.coroutines.CoroutineContext

class StreamDownload(
    private val ccontext: CoroutineContext = GlobalScope.coroutineContext,
    private val booru: Booru
) {
    private val defaultSize = 16384
    private var start = 0
    private var end = 0
    private val buffer = ByteArrayOutputStream()
    private val listener = StreamDownloadListener()

    fun download(url: String) = GlobalScope.launch(ccontext) {
        val length = booru.headCustom().request(url).length
        var count = 0

        while (true) {
            start = end - 1
            end += defaultSize
            val response = booru.getCustom(mapOf("Range" to "bytes=$start-$end")).request(url)
            val bytes = response.stream.readBytes()
            buffer.write(bytes)

            count += bytes.size
            listener.invokeOnPartReceived(length, bytes, (count.toFloat() / length) - 1)

            //when stream is ends
            if (bytes.size < defaultSize) {
                listener.invokeOnComplete(buffer.toByteArray())
                break
            }
        }
    }

    fun addListener(listener: StreamDownloadListener.() -> Unit) {
        listener.invoke(this.listener)
    }
}