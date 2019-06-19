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
        val defaultResult = getNetworkExecutor.perform(request, *params)
        // log default network execution result
        getNetworkExecutor.logResponse(request, defaultResult)
        if (defaultResult.error == null) return defaultResult
        // perform proxied network execution
        val proxiedResult = proxyNetworkExecutor.perform(request, *params)
        // log proxy network execution result
        if (DEBUG) proxyNetworkExecutor.logResponse(request, proxiedResult)
        return proxiedResult
    }

    private fun NetworkExecutor.logResponse(request: Request, response: Response) {
        val logmessage = StringBuilder().append("ResultCode=").append(response.code).append("\n")
            .append("Request=").append(request.url).append("\n")
            .append("Error=").append(response.error).append("\n")
            .append("Headers=").append(response.headers)
        Logger.getLogger(this::class.java.simpleName).warning(logmessage.toString())
    }
}