package com.makentoshe.imageview.download

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.makentoshe.imageview.ImageDecoder

class CustomNotificationExecutor(
    private val context: Context, private val imageDecoder: ImageDecoder
) : NotificationExecutor {

    /** Returns a channel id */
    private val channelId = context.applicationInfo.loadLabel(context.packageManager).toString()

    /** Returns a notification manager*/
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    /** Returns a notification builder for a specific android version */
    private val builder: NotificationCompat.Builder
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
            NotificationCompat.Builder(context, channelId).setChannelId(channelId)
        } else {
            NotificationCompat.Builder(context, channelId)
        }

    override fun notifySuccess(id: Long, title: String, content: String, image: ByteArray) {
        val notification = builder.setContentTitle(title).setContentText(content)
            .setSmallIcon(com.makentoshe.style.R.drawable.ic_launcher_mono)
            .setLargeIcon(imageDecoder.decode(image)).build()
        notificationManager.notify(id.toInt(), notification)
    }

    override fun notifyProgress(id: Long, title: String, content: String, progress: Int) {
        val notification = builder.setContentTitle(title).setContentText(content)
            .setSmallIcon(com.makentoshe.style.R.drawable.ic_launcher_mono)
            .apply { if (progress >= 0) setProgress(100, progress, false) else setProgress(100, 100, true) }.build()
        notificationManager.notify(id.toInt(), notification)
    }

    override fun notifyError(id: Long, title: String, exception: Throwable) {
        val notification = builder.setContentTitle(title).setContentText(exception.localizedMessage)
            .setSmallIcon(com.makentoshe.style.R.drawable.ic_launcher_mono).build()
        notificationManager.notify(id.toInt(), notification)
    }
}