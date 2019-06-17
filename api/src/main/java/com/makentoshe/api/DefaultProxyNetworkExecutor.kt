package com.makentoshe.api

import com.makentoshe.boorulibrary.network.Response
import com.makentoshe.boorulibrary.network.executor.NetworkExecutor
import com.makentoshe.boorulibrary.network.request.EmptyRequest
import com.makentoshe.boorulibrary.network.request.Request
import com.makentoshe.boorulibrary.network.request.RequestBuilder

/**
 * Special class for perform proxy network request.
 *
 * @param defNetworkExecutor is a [NetworkExecutor] which is performs a network request using
 * received proxied url.
 * @param altNetworkExecutor is a [NetworkExecutor] which is performs a network request and
 * returns a proxied url.
 */
open class DefaultProxyNetworkExecutor(
    private val altNetworkExecutor: NetworkExecutor,
    defNetworkExecutor: NetworkExecutor
) : ProxyNetworkExecutor(defNetworkExecutor) {

    override val proxyUrl = "http://service.bypass123.com/index.php"

    /** Makes a POST request to the proxy and returns a proxied url */
    override suspend fun performNetworkRequestToProxy(request: Request): Pair<Request, Response> {
        val response = altNetworkExecutor.perform(request)
        val locationUrl = response.headers["Location"]?.getOrNull(0) ?: return EmptyRequest to response
        return RequestBuilder().build(locationUrl) to response
    }
}