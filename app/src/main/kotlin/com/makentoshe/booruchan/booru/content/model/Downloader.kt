package com.makentoshe.booruchan.booru.content.model

import com.makentoshe.booruchan.common.api.HttpClient
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import java.io.InputStream
import java.lang.Exception

class Downloader(val client: HttpClient) {

    fun download(url: String, action: (InputStream?) -> (Unit)): Job {
        return GlobalScope.launch { action(client.get(url).stream()) }
    }

}