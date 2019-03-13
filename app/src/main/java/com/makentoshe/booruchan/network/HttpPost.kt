package com.makentoshe.booruchan.network

import java.io.Serializable

abstract class HttpPost(protected val url: String) : HttpResult, Serializable