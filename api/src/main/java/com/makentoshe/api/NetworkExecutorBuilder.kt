package com.makentoshe.api

import com.makentoshe.boorulibrary.network.executor.DefaultGetNetworkExecutor
import com.makentoshe.boorulibrary.network.executor.DefaultPostNetworkExecutor
import com.makentoshe.boorulibrary.network.executor.DefaultStreamGetNetworkExecutor

class NetworkExecutorBuilder {

    private var shouldUseProxy = false

    fun useProxy(value: Boolean): NetworkExecutorBuilder {
        shouldUseProxy = value
        return this
    }

    fun build() = if (shouldUseProxy) {
        val alt = DefaultPostNetworkExecutor()
        val def = DefaultGetNetworkExecutor()
        DefaultProxyNetworkExecutor(alt, def)
    } else {
        DefaultStreamGetNetworkExecutor()
    }

}