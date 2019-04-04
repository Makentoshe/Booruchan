package com.makentoshe.booruchan.screen.samples.model

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Post
import org.jetbrains.anko.doFromSdk
import org.jetbrains.anko.notificationManager

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
            val notificationChannel =
                NotificationChannel(appName, appName, NotificationManager.IMPORTANCE_DEFAULT)
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