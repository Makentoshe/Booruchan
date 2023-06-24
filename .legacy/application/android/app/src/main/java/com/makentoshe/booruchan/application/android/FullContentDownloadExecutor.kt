package com.makentoshe.booruchan.application.android

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
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

    // TODO test method on Q(10) sdk
    fun downloadPostFullContent(post: Post) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        downloadPostFullContentQ(post)
    } else {
        downloadPostFullContentDefault(post)
    }

    private fun downloadPostFullContentDefault(post: Post) {
        val externalStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val relativePath = "${applicationContext.getString(R.string.app_name)}${File.separator}$title"
        val directory = File(externalStorage, relativePath)

        val content = File(directory, "${post.postId}.${post.fullContent.extension}")
        if (content.exists()) {
            capture(Log.DEBUG, "$content already exists.")
            listener?.onFinishDownload(content, Result.failure<Any>(FileAlreadyExistsException(directory)))
        } else {
            directory.mkdirs()
            capture(Log.DEBUG, "Create parent directory for $directory")
            listener?.onStartDownload(content, post.fullContent)
            coroutineScope.launch(Dispatchers.IO) {
                val response = arena.suspendFetch(post.fullContent)
                response.onSuccess { content.writeBytes(it) }
                launch(Dispatchers.Main) { listener?.onFinishDownload(content, response) }
            }
        }
    }

    private fun downloadPostFullContentQ(post: Post) {
        val applicationName = applicationContext.getString(R.string.app_name)
        val contentTitle = "${post.postId}.${post.fullContent.extension}"
        val directory = Environment.DIRECTORY_DOWNLOADS + File.separator + applicationName + File.separator + title

        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, contentTitle)
        contentValues.put(MediaStore.MediaColumns.TITLE, contentTitle)
        // without this part causes "Failed to create new MediaStore record" exception to be invoked (uri is null below)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, directory)
        }

        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val contentResolver = applicationContext.contentResolver
        val uri = contentResolver.insert(contentUri, contentValues)!!

//        contentResolver.query(uri, null, null, null, null).use {
//            DatabaseUtils.dumpCursor(it)
//        }

        coroutineScope.launch(Dispatchers.IO) {
            arena.suspendFetch(post.fullContent).onSuccess { byteArray ->
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                contentResolver.openOutputStream(uri).use { stream ->
                    println(bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream))
                }
            }
        }

//        Snackbar.make(mCoordinator, R.string.image_saved_success, Snackbar.LENGTH_INDEFINITE).setAction("Open") {
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_VIEW
//            intent.data = contentUri
//            startActivity(Intent.createChooser(intent, "Select Gallery App"))
//        }.show()
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