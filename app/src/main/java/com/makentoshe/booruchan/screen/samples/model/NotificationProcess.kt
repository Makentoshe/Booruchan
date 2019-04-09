package com.makentoshe.booruchan.notification

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
        val params = NotificationBuilder.Params(context, appName)

        NotificationBuilder().build(params).applyAdditionalParams(apply).notify(context, post.id.toInt())
    }
}