package com.makentoshe.booruchan.booru.model.gallery.common

import com.makentoshe.booruchan.common.api.HttpClient
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import java.io.InputStream
import java.lang.Exception

class Downloader(val client: HttpClient) {

    fun download(url: String, action: (InputStream?) -> (Unit)): Job {
        return GlobalScope.launch {
            var errorWasNotShown = true
            do {
                try {
                    action(client.get(url).stream())
                    break
                } catch (e: Exception) {
                    if (errorWasNotShown) {
                        errorWasNotShown = false
                        action(null)
                    }
                    Thread.yield()
                }
            } while (true)
        }
    }

}