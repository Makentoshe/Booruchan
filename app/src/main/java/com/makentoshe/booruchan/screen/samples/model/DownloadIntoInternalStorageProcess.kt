package com.makentoshe.booruchan.screen.samples.model

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Environment
import android.os.Handler
import android.os.Looper
import com.google.android.material.snackbar.Snackbar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.permission.PermissionRequester
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.longToast
import java.io.File
import java.io.FileOutputStream
import kotlin.coroutines.CoroutineContext

class DownloadIntoInternalStorageProcess(private val post: Post, private val booru: Booru) {

    fun start(context: Context, permissionRequester: PermissionRequester) {
        context.longToast(context.getString(R.string.download_was_started)).show()
        permissionRequester.requestPermission(permission) {
            doOnPermissionRequestResult(context, it)
        }
    }

    private fun doOnPermissionRequestResult(context: Context, it: Boolean) = if (it) {
        onPermissionGranted(context)
    } else {
        onPermissionDenied(context)
    }

    private fun onPermissionGranted(context: Context) {
        //download file
        DownloadProcess(booru).start(post) { downloadedData, throwable ->
            //if exception was not thrown
            if (throwable == null) {
                SaveProcess(context).start(downloadedData!!)
                fileWasSuccessfullyLoaded(context)
            } else {
                fileWasUnsuccessfullyLoaded(context, throwable.localizedMessage)
            }
        }
    }

    private fun onPermissionDenied(context: Context) {
        permissionDeniedCallback(context)
    }

    /* Calls when file was successfully downloaded */
    private fun fileWasSuccessfullyLoaded(context: Context) {
        val message = StringBuilder(context.getString(R.string.file)).append(" ")
        message.append(context.getString(R.string.was_loaded_succesfully))
        sendNotificationWithMessage(context, message)
    }

    /* Calls when error occurs while file was downloading */
    private fun fileWasUnsuccessfullyLoaded(context: Context, reason: String) {
        val message = StringBuilder(context.getString(R.string.image_was_not_loaded_succesfully))
        if (!reason.isBlank()) message.append("\n").append(reason)
        sendNotificationWithMessage(context, message)
    }

    /* Calls when write external storage permission was denied */
    private fun permissionDeniedCallback(context: Context) {
        val message = StringBuilder(context.getString(R.string.download_was_not_started))
            .append("\n").append(context.getString(R.string.permission_denied))
        sendNotificationWithMessage(context, message)
    }

    private fun sendNotificationWithMessage(context: Context, message: CharSequence) {
        if (context is Activity) {
            val view = context.window.decorView
            Snackbar.make(
                view,
                message,
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            context.longToast(message).show()
        }
    }

    companion object {
        private const val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    }
}

class DownloadProcess(
    private val booru: Booru,
    private val ccontext: CoroutineContext = GlobalScope.coroutineContext
) {

    fun start(post: Post, result: (DownloadedData?, Throwable?) -> Unit) {
        //start a new coroutine for loading an image
        GlobalScope.launch(ccontext) {
            try {
                //perform loading and reading bytes
                val bytes = booru.getCustom().request(post.fileUrl).stream.readBytes()
                //box all data into the data class
                val data = bytes.toDownloadData(post, booru)
                //return to main thread
                Handler(Looper.getMainLooper()).post { result(data, null) }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post { result(null, e) }
            }
        }
    }

    private fun ByteArray.toDownloadData(post: Post, booru: Booru): DownloadedData {
        val title = post.id.toString()
        val extension = File(post.fileUrl).extension
        return DownloadedData(this, title, extension, booru.title)
    }
}

data class DownloadedData(
    val byteArray: ByteArray,
    val title: String,
    val extension: String,
    val booruTitle: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DownloadedData

        if (!byteArray.contentEquals(other.byteArray)) return false
        if (title != other.title) return false
        if (extension != other.extension) return false
        if (booruTitle != other.booruTitle) return false

        return true
    }

    override fun hashCode(): Int {
        var result = byteArray.contentHashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + extension.hashCode()
        result = 31 * result + booruTitle.hashCode()
        return result
    }
}

class SaveProcess(private val context: Context) {

    /* Saved the data to the internal storage */
    fun start(data: DownloadedData) {
        //create image file
        val imageDir = getImageDirectory(data.booruTitle)
        val imagefile = File(imageDir, "${data.title}.${data.extension}")
        imagefile.createNewFile()
        //write to it
        FileOutputStream(imagefile).use { fos ->
            fos.write(data.byteArray)
            fos.flush()
        }
    }

    /* Returns the calculated image directory based on booru title */
    private fun getImageDirectory(title: String): File {
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val appDir = context.getString(R.string.app_name)
        val imageDir = File(root, "${File.separator}$appDir${File.separator}$title")
        return imageDir.apply { mkdirs() }
    }
}