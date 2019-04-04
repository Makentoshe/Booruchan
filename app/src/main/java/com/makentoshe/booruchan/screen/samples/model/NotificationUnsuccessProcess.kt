package com.makentoshe.booruchan.screen.samples.model

import android.content.Context
import androidx.core.app.NotificationCompat
import com.makentoshe.booruchan.R

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