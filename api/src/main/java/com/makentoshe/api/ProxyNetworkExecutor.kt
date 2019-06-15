package com.makentoshe.api

import android.accounts.NetworkErrorException
import com.makentoshe.boorulibrary.network.DefaultResponse
import com.makentoshe.boorulibrary.network.DownloadListener
import com.makentoshe.boorulibrary.network.Response
import com.makentoshe.boorulibrary.network.executor.NetworkExecutor
import com.makentoshe.boorulibrary.network.request.EmptyRequest
import com.makentoshe.boorulibrary.network.request.Request
import com.makentoshe.boorulibrary.network.request.RequestBuilder
import java.net.URLEncoder

/**
 * Special class for perform proxy network request.
 *
 * @param defNetworkExecutor is a [NetworkExecutor] which is performs a network request using
 * received proxied url.
 */
abstract class ProxyNetworkExecutor(private val defNetworkExecutor: NetworkExecutor) : NetworkExecutor {

    protected abstract val proxyUrl: String

    override suspend fun perform(request: Request, vararg params: Pair<String, String>): Response {
        //get request to proxy for receiving a proxied url
        val requestToProxy = buildRequestToProxy(request.url)
        //perform network request to a proxy
        val proxiedRequest = performNetworkRequestToProxy(requestToProxy)

        if (proxiedRequest.first is EmptyRequest) {
            val code = proxiedRequest.second.code
            val headers = proxiedRequest.second.headers
            val bytes = proxiedRequest.second.bytes
            return DefaultResponse(
                code, proxiedRequest.first, bytes, headers,
                NetworkErrorException("Proxy response does not contains redirect url"))
        }
        //perform default network request using proxy url
        return defNetworkExecutor.perform(proxiedRequest.first, *params)
    }

    protected open fun buildRequestToProxy(url: String): Request {
        val body = "url=${URLEncoder.encode(url, "UTF-8")}"
        return RequestBuilder().body(body.toByteArray()).build(proxyUrl)
    }

    protected abstract suspend fun performNetworkRequestToProxy(request: Request): Pair<Request, Response>
}

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

/**
 * Interface for proxied download events. This interface used for default listener with proxied address.
 */
interface ProxyDownloadListener : DownloadListener {

    /** Listener for proxy events */
    val proxyListener: DownloadListener?
}


abstract class SimpleProxyDownloadListener(override val proxyListener: DownloadListener): ProxyDownloadListener