package com.makentoshe.booruchan.model

interface StreamDownloadListener {

    fun onError(l: (Throwable) -> Unit)

    fun onComplete(l: (ByteArray) -> Unit)

    fun onPartReceived(l: (Long, ByteArray, Float) -> Unit)
}

interface StreamDownloadController : StreamDownloadListener {

    fun invokeOnComplete(byteArray: ByteArray)

    fun invokeOnPartReceived(length: Long, byteArray: ByteArray, progress: Float)

    fun invokeOnError(throwable: Throwable)

    companion object {
        fun create(): StreamDownloadController {
            return object : StreamDownloadController {

                private val onCompleteList = ArrayList<(ByteArray) -> Unit>()
                private val onPartReceivedList = ArrayList<(Long, ByteArray, Float) -> Unit>()
                private val onErrorList = ArrayList<(Throwable) -> Unit>()

                override fun invokeOnComplete(byteArray: ByteArray) {
                    onCompleteList.forEach { it.invoke(byteArray) }
                }

                override fun onError(l: (Throwable) -> Unit) {
                    onErrorList.add(l)
                }

                override fun invokeOnPartReceived(length: Long, byteArray: ByteArray, progress: Float) {
                    onPartReceivedList.forEach { it.invoke(length, byteArray, progress) }
                }

                override fun onComplete(l: (ByteArray) -> Unit) {
                    onCompleteList.add(l)
                }

                override fun onPartReceived(l: (Long, ByteArray, Float) -> Unit) {
                    onPartReceivedList.add(l)
                }

                override fun invokeOnError(throwable: Throwable) {
                    onErrorList.forEach { it.invoke(throwable) }
                }
            }
        }
    }

}
