package com.makentoshe.booruchan.notification

import android.content.Context
import androidx.core.app.NotificationCompat
import org.jetbrains.anko.notificationManager

class CustomNotification(private val builder: NotificationCompat.Builder) {

    fun applyAdditionalParams(action: NotificationCompat.Builder.() -> Unit): CustomNotification {
        action(builder)
        return this
    }

    fun notify(context: Context, id: Int) {
        context.notificationManager.notify(id, builder.build())
    }
}

