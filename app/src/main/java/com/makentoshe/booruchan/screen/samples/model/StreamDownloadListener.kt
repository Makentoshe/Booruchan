package com.makentoshe.booruchan.screen.samples.model

class StreamDownloadListener {

    private val onCompleteList = ArrayList<(ByteArray) -> Unit>()
    private val onPartReceivedList = ArrayList<(Long, ByteArray, Float) -> Unit>()
    private val onErrorList = ArrayList<(Throwable) -> Unit>()

    fun invokeOnComplete(byteArray: ByteArray) {
        onCompleteList.forEach { it.invoke(byteArray) }
    }

    fun onError(l: (Throwable) -> Unit) {
        onErrorList.add(l)
    }

    fun invokeOnPartReceived(length: Long, byteArray: ByteArray, progress: Float) {
        onPartReceivedList.forEach { it.invoke(length, byteArray, progress) }
    }

    fun onComplete(l: (ByteArray) -> Unit) {
        onCompleteList.add(l)
    }

    fun onPartReceived(l: (Long, ByteArray, Float) -> Unit) {
        onPartReceivedList.add(l)
    }

    fun invokeOnError(throwable: Throwable) {
        onErrorList.forEach { it.invoke(throwable) }
    }
}