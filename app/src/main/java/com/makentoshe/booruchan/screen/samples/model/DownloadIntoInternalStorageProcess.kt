package com.makentoshe.booruchan.screen.samples.model

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.google.android.material.snackbar.Snackbar
import com.makentoshe.booruchan.AppBroadcastReceiver
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.permission.PermissionRequester
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doFromSdk
import org.jetbrains.anko.longToast
import org.jetbrains.anko.notificationManager
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
        DownloadProcess(booru).start(post) { downloadedData, throwable ->
            if (throwable == null) {
                SaveProcess(context).start(downloadedData!!)
                fileWasSuccessfullyLoaded(context, downloadedData)
            } else {
                fileWasUnsuccessfullyLoaded(context, throwable)
            }
        }
    }

    private fun onPermissionDenied(context: Context) {
        permissionDeniedCallback(context)
    }

    /* Calls when file was successfully downloaded and displays a notification message */
    private fun fileWasSuccessfullyLoaded(context: Context, downloadedData: DownloadedData) {
        NotificationSuccessProcess(downloadedData, NotificationProcess(post)).start(context)
    }

    /* Calls when error occurs while file was downloading */
    private fun fileWasUnsuccessfullyLoaded(context: Context, throwable: Throwable) {
        NotificationUnsuccessProcess(throwable, NotificationProcess(post)).start(context)
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
        val imageDir = getImageDirectory(context, data.booruTitle)
        val imagefile = File(imageDir, "${data.title}.${data.extension}")
        imagefile.createNewFile()
        //write to it
        FileOutputStream(imagefile).use { fos ->
            fos.write(data.byteArray)
            fos.flush()
        }
    }

    companion object {
        /* Returns the calculated image directory based on booru title */
        fun getImageDirectory(context: Context, title: String): File {
            val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val appDir = context.getString(R.string.app_name)
            val imageDir = File(root, "${File.separator}$appDir${File.separator}$title")
            return imageDir.apply { mkdirs() }
        }
    }
}

class NotificationProcess(private val post: Post) {

    @SuppressLint("NewApi")
    fun start(context: Context, apply: NotificationCompat.Builder.() -> Unit) {
        val appName = context.getString(R.string.app_name)
        val builder = NotificationCompat.Builder(context, appName)
        //set application icon
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        //set application name as a title
        builder.setContentTitle(appName)

        builder.onOreo {
            val notificationChannel = NotificationChannel(appName, appName, NotificationManager.IMPORTANCE_DEFAULT)
            context.notificationManager.createNotificationChannel(notificationChannel)
            builder.setChannelId(notificationChannel.id)
        }
        //custom settings
        apply(builder)
        //show notification
        context.notificationManager.notify(post.id.toInt(), builder.build())
    }

    private fun NotificationCompat.Builder.onOreo(action: (NotificationCompat.Builder) -> Unit) {
        doFromSdk(Build.VERSION_CODES.O) { action(this) }
    }
}

class NotificationSuccessProcess(
    private val downloadedData: DownloadedData,
    private val notificationProcess: NotificationProcess
) {
    fun start(context: Context) {
        notificationProcess.start(context) {
            //set notification text
            setText(context)
            //set preview large icon
            setPreview(downloadedData.byteArray)
            //set on notification click listener
            onClick(context)
        }
    }

    private fun NotificationCompat.Builder.setPreview(byteArray: ByteArray) {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        setLargeIcon(bitmap)
    }

    private fun NotificationCompat.Builder.setText(context: Context) {
        val message = StringBuilder(context.getString(R.string.file)).append(" ")
        message.append(context.getString(R.string.was_loaded_succesfully))
        setContentText(message)
    }

    private fun NotificationCompat.Builder.onClick(context: Context) {
        val imageDir = SaveProcess.getImageDirectory(context, downloadedData.booruTitle)
        val imagefile = File(imageDir, "${downloadedData.title}.${downloadedData.extension}")
        val intent = Intent(context, AppBroadcastReceiver::class.java)
        if (imagefile.extension == "webm") {
            intent.putExtra(AppBroadcastReceiver.videoType, imagefile.toString())
        } else {
            intent.putExtra(AppBroadcastReceiver.imageType, imagefile.toString())
        }
        setContentIntent(PendingIntent.getBroadcast(context, downloadedData.title.toInt(), intent, 0))
    }
}

class NotificationUnsuccessProcess(
    private val throwable: Throwable,
    private val notificationProcess: NotificationProcess
) {
    fun start(context: Context) {
        notificationProcess.start(context) {
            setText(context)
//            onClick(context)
        }
    }

    private fun NotificationCompat.Builder.setText(context: Context) {
        val message = StringBuilder(context.getString(R.string.file)).append(" ")
        message.append(context.getString(R.string.was_not_loaded_succesfully))
        setContentText(message)
    }

}