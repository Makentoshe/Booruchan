package com.makentoshe.booruchan.application.android

import android.content.Context
import android.os.Environment
import android.util.Log
import com.makentoshe.booruchan.application.core.arena.Arena
import com.makentoshe.booruchan.application.core.arena.EmptyArenaStorage
import com.makentoshe.booruchan.application.core.arena.post.PostContentArena
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Content
import com.makentoshe.booruchan.core.post.Post
import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.File

// TODO move download to IntentService
class FullContentDownloadExecutor(
    private val arena: Arena<Content, ByteArray>,
    private val title: String,
    private val applicationContext: Context,
    private val listener: DownloadListener?
) {

    companion object {
        fun capture(level: Int, message: String) {
            Log.println(level, "FullContentDownloader", message)
        }
    }

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun downloadPostFullContent(post: Post) {
        val directory = defineDirectoryLocation(post)
        if (directory.exists()) {
            capture(Log.DEBUG, "$directory already exists.")
            listener?.onFinishDownload(directory, Result.failure<Any>(FileAlreadyExistsException(directory)))
            return
        }

        directory.parentFile?.mkdirs()
        capture(Log.DEBUG, "Create parent directory for $directory")
        listener?.onStartDownload(directory, post.fullContent)
        coroutineScope.launch(Dispatchers.IO) {
            val response = arena.suspendFetch(post.fullContent)
            response.onSuccess { directory.writeBytes(it) }
            launch(Dispatchers.Main) { listener?.onFinishDownload(directory, response) }
        }
    }

    private fun defineDirectoryLocation(post: Post): File {
        // TODO fix external dir
        val downloads = applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val applicationTitle = applicationContext.getString(R.string.app_name)
        val image = "${post.postId}.${post.fullContent.extension}"
        return File(downloads, "$applicationTitle/$title/$image")
    }

    class Builder(private val client: HttpClient, private val applicationContext: Context) {

        fun build(booruContext: BooruContext, listener: DownloadListener?): FullContentDownloadExecutor {
            val arena = PostContentArena(client, EmptyArenaStorage())
            return FullContentDownloadExecutor(arena, booruContext.title, applicationContext, listener)
        }
    }

    interface DownloadListener {

        fun onStartDownload(directory: File, content: Content)

        fun onFinishDownload(directory: File, result: Result<*>)
    }
}