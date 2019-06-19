package com.makentoshe.api

import com.makentoshe.boorulibrary.network.DownloadListener

/**
 * Interface for proxied download events. This interface used for default listener with proxied address.
 */
interface ComposeDownloadListener<T: DownloadListener> : DownloadListener {

    /** Listener for default events */
    val listener: T?
}