package com.makentoshe.api

import com.makentoshe.boorulibrary.network.DownloadListener
import com.makentoshe.boorulibrary.network.StreamDownloadListener
import com.makentoshe.boorulibrary.network.executor.DefaultGetNetworkExecutor
import com.makentoshe.boorulibrary.network.executor.DefaultPostNetworkExecutor
import com.makentoshe.boorulibrary.network.executor.StreamGetNetworkExecutor

class NetworkExecutorBuilder {

    companion object {

        fun buildGet(listener: DownloadListener? = null) = DefaultGetNetworkExecutor(listener)

        fun buildStreamGet(listener: StreamDownloadListener? = null) = StreamGetNetworkExecutor(listener)

        fun buildProxyGet(composeDownloadListener: ComposeDownloadListener<*>? = null): ProxyNetworkExecutor {
            val alt = DefaultPostNetworkExecutor(composeDownloadListener?.listener)
            val def = DefaultGetNetworkExecutor(composeDownloadListener)
            return DefaultProxyNetworkExecutor(alt, def)
        }

        fun buildSmartGet(
            composeDownloadListener: ComposeDownloadListener<*>? = null,
            streamDownloadListener: StreamDownloadListener? = null
        ): SmartNetworkExecutor {
            val proxyExecutor = buildProxyGet(composeDownloadListener)
            val defaultExecutor = buildStreamGet(streamDownloadListener)
            return SmartNetworkExecutor(proxyExecutor, defaultExecutor)
        }
    }

}