package com.makentoshe.booruchan.repository.stream

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.model.StreamDownloadController
import com.makentoshe.booruchan.repository.Repository
import java.io.ByteArrayOutputStream

/**
 * Repository for streaming downloads.
 * If streaming download was not supports, a file will be downloaded wholly.
 */
class StreamDownloadRepository(
    private val listener: StreamDownloadController? = null,
    private val booru: Booru
) : Repository<String, ByteArray> {

    private var start = 0
    private var end = 1
    private val buffer = ByteArrayOutputStream()
    private val bufferSize: Int = 16384

    override fun get(key: String): ByteArray {
        //check file length in bytes
        val length = booru.headCustom().request(key).length
        //if length was not calculated (mb use proxy) just load
        return if (length == -1L) loadWholly(key) else loadParts(length, key)
    }

    private fun loadWholly(url: String): ByteArray {
        return booru.getCustom().request(url).stream.readBytes()
    }

    private fun loadParts(length: Long, url: String): ByteArray {
        var count = 0
        while (true) {
            //calc segment
            start = end - 1
            end += bufferSize
            //get segment bytes and write to buffer
            val response = booru.getCustom(mapOf("Range" to "bytes=$start-$end")).request(url)
            val bytes = response.stream.readBytes()
            buffer.write(bytes)
            //invoke listener the segment was received
            count += bytes.size
            listener?.invokeOnPartReceived(length, bytes, (count.toFloat() / length) - 1)

            //when stream is ends
            if (bytes.size < bufferSize) {
                //invoke listener and finish
                val array = buffer.toByteArray()
                listener?.invokeOnComplete(array)
                return array
            }
        }
    }
}