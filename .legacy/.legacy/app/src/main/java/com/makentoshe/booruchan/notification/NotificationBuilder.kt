package com.makentoshe.booruchan.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.makentoshe.booruchan.R
import org.jetbrains.anko.notificationManager

class NotificationBuilder {

    fun build(param: Params): CustomNotification {
        val builder = getBuilder(param.context)
        builder.setContentTitle(param.title)
        builder.setContentText(param.text)
        builder.setSmallIcon(param.smallIconResource.id)
        builder.color = Color.TRANSPARENT
        return CustomNotification(builder)
    }

    private fun getBuilder(context: Context): NotificationCompat.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getOreoBuilder(context)
        } else {
            getDefaultBuilder(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getOreoBuilder(context: Context): NotificationCompat.Builder {
        val channel = getChannel(context)
        return NotificationCompat.Builder(context, channel.id).setChannelId(channel.id)
    }

    private fun getDefaultBuilder(context: Context): NotificationCompat.Builder {
        val channelId = getChannelId(context)
        return NotificationCompat.Builder(context, channelId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getChannel(context: Context): NotificationChannel {
        val channelId = getChannelId(context)
        val channel = NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT)
        context.notificationManager.createNotificationChannel(channel)
        return channel
    }

    private fun getChannelId(context: Context): String {
        return context.getString(R.string.app_name)
    }

    data class Params(
        val context: Context,
        val title: String? = null,
        val smallIconResource: IconResource = IconResource(R.drawable.ic_launcher_mono),
        val text: String? = null
    )

}