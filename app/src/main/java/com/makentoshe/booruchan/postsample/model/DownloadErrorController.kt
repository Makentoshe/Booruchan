package com.makentoshe.booruchan.postsample.model

import java.lang.Exception

interface DownloadErrorController {
    fun push(exception: Exception)
}