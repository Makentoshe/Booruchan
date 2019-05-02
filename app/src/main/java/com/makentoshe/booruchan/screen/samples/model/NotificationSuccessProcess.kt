package com.makentoshe.booruchan.screen.samples.model

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.makentoshe.booruchan.AppBroadcastReceiver
import com.makentoshe.booruchan.R
import java.io.File

class NotificationSuccessProcess(
    private val downloadedData: DownloadedData,
    private val notificationProcess: NotificationProcess
) {
    fun start(context: Context) {
        notificationProcess.start(context) {
            //tags notification text
            setText(context)
            //tags preview large icon
            setPreview(downloadedData.byteArray)
            //tags on notification click listener
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
        val imageDir = ExternalStorageSave.getImageDirectory(
            context,
            downloadedData.booruTitle
        )
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