package com.makentoshe.api

import com.makentoshe.boorulibrary.network.StreamDownloadListener
import com.makentoshe.boorulibrary.network.executor.DefaultGetNetworkExecutor
import com.makentoshe.boorulibrary.network.executor.DefaultPostNetworkExecutor
import com.makentoshe.boorulibrary.network.executor.StreamGetNetworkExecutor

class NetworkExecutorBuilder {

    companion object {

        fun buildStreamGet(listener: StreamDownloadListener? = null) = StreamGetNetworkExecutor(listener)

        fun buildProxyGet(proxyDownloadListener: ProxyDownloadListener? = null): ProxyNetworkExecutor {
            val alt = DefaultPostNetworkExecutor(proxyDownloadListener?.proxyListener)
            val def = DefaultGetNetworkExecutor(proxyDownloadListener)
            return DefaultProxyNetworkExecutor(alt, def)
        }
    }

}