package com.makentoshe.booruchan.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.report.ReportActivity
import java.io.PrintWriter
import java.io.StringWriter


class NotificationUnsuccessProcess(
    private val throwable: Throwable,
    private val id: Int,
    private val notificationProcess: NotificationProcess
) {
    fun start(context: Context) {
        notificationProcess.start(context) {
            setText(context)
            onClick(context)
            setAutoCancel(true)
        }
    }

    private fun NotificationCompat.Builder.setText(context: Context) {
        setContentText(getMessage(context))
    }

    private fun NotificationCompat.Builder.onClick(context: Context) {
        val intent = Intent(context, ReportActivity::class.java)
        intent.putExtra(ReportActivity.body, throwable.getStackTraceString())
        intent.putExtra(ReportActivity.title, getMessage(context))
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pintent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        setContentIntent(pintent)
    }

    private fun getMessage(context: Context): String {
        val message = StringBuilder(context.getString(R.string.file)).append(" ")
        message.append(context.getString(R.string.was_not_loaded_succesfully))
        return message.toString()
    }

    private fun Throwable.getStackTraceString(): String {
        val sw = StringWriter()
        printStackTrace(PrintWriter(sw))
        return sw.toString()
    }

}