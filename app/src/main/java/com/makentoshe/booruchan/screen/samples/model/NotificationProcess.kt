package com.makentoshe.booruchan.screen.samples.model

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.app.NotificationCompat
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.notification.NotificationBuilder

class NotificationProcess(private val post: Post) {

    @SuppressLint("NewApi")
    fun start(context: Context, apply: NotificationCompat.Builder.() -> Unit) {
        val appName = context.getString(R.string.app_name)
        val params = NotificationBuilder.Params(context, appName)

        NotificationBuilder().build(params).applyAdditionalParams(apply).notify(context, post.id.toInt())
    }
}