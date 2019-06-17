package com.makentoshe.api

import com.makentoshe.api.BuildConfig.DEBUG
import com.makentoshe.boorulibrary.network.Response
import com.makentoshe.boorulibrary.network.executor.NetworkExecutor
import com.makentoshe.boorulibrary.network.request.Request
import java.util.logging.Logger

/**
 * Network executor uses proxy if connection is blocked by peer.
 */
class SmartNetworkExecutor(
    private val proxyNetworkExecutor: ProxyNetworkExecutor,
    private val getNetworkExecutor: NetworkExecutor
) : NetworkExecutor {

    override suspend fun perform(request: Request, vararg params: Pair<String, String>): Response {
        val result = getNetworkExecutor.perform(request, *params)
        return if (result.error == null) onSuccess(request, result) else onError(request, result, *params)
    }

    /** Default network execution was finished successfully */
    private fun onSuccess(request: Request, response: Response) = response.also {
        if (DEBUG && (response.code / 100) != 2) logResponse(request, response)
    }

    /** Default network execution was finished with some errors */
    private suspend fun onError(request: Request, response: Response, vararg params: Pair<String, String>): Response {
        return proxyNetworkExecutor.perform(request, *params).also {
            if (DEBUG) logResponse(request, it)
        }
    }

    private fun logResponse(request: Request, response: Response) {
        val logmessage = StringBuilder().append("ResultCode=").append(response.code).append("\n")
            .append("Request=").append(request).append("\n")
            .append("Error=").append(response.error).append("\n")
            .append("Headers=").append(response.headers).append("\n \n")
        Logger.getLogger(this::class.java.simpleName).warning(logmessage.toString())
    }
}