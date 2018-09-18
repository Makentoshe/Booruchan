package com.makentoshe.booruchan.booru.model.gallery.common

import com.makentoshe.booruchan.common.api.HttpClient
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import java.io.InputStream

class Downloader(val client: HttpClient) {

    fun download(url: String, action: (InputStream) -> (Unit)): Job {
        return GlobalScope.launch {
            action(client.get(url).stream())
        }
    }

}