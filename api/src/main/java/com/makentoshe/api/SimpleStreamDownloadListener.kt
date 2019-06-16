package com.makentoshe.api

import com.makentoshe.boorulibrary.network.Response
import com.makentoshe.boorulibrary.network.StreamDownloadListener
import com.makentoshe.boorulibrary.network.request.Request

class SimpleStreamDownloadListener : StreamDownloadListener {

    private var onDownloading: ((ByteArray, Int) -> Unit)? = null
    private var onError: ((Response) -> Unit)? = null
    private var onFInish: (() -> Unit)? = null
    private var onStart: ((Request, Array<out Pair<String, String>>) -> Unit)? = null
    private var onSuccess: ((Response) -> Unit)? = null

    fun onDownloading(l: (ByteArray, Int) -> Unit) {
        onDownloading = l
    }

    override fun onDownloading(bytes: ByteArray, limit: Int) {
        onDownloading?.invoke(bytes, limit)
    }

    fun onError(l: (Response) -> Unit) {
        onError = l
    }

    override fun onError(response: Response) {
        onError?.invoke(response)
    }

    fun onFinish(l: () -> Unit) {
        onFInish = l
    }

    override fun onFinish() {
        onFInish?.invoke()
    }

    fun onStart(l: (Request, Array<out Pair<String, String>>) -> Unit) {
        onStart = l
    }

    override fun onStart(request: Request, params: Array<out Pair<String, String>>) {
        onStart?.invoke(request, params)
    }

    fun onSuccess(l: (Response) -> Unit) {
        onSuccess = l
    }

    override fun onSuccess(response: Response) {
        onSuccess?.invoke(response)
    }
}