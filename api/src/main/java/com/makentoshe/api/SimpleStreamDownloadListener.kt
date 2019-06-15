package com.makentoshe.api

import com.makentoshe.boorulibrary.network.Response
import com.makentoshe.boorulibrary.network.StreamDownloadListener
import com.makentoshe.boorulibrary.network.request.Request

abstract class SimpleStreamDownloadListener(): StreamDownloadListener {

    override fun onDownloading(bytes: ByteArray, limit: Int) = Unit
    override fun onFinish() = Unit
    override fun onStart(request: Request, params: Array<out Pair<String, String>>) = Unit
    override fun onSuccess(response: Response) = Unit
    override fun onError(response: Response) = Unit
}