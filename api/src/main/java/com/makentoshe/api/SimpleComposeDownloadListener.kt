package com.makentoshe.api

import com.makentoshe.boorulibrary.network.DownloadListener

abstract class SimpleComposeDownloadListener(override val proxyListener: DownloadListener): ComposeDownloadListener