package com.makentoshe.booruchan.screen.samples.model

import android.Manifest
import android.app.Activity
import android.content.Context
import com.google.android.material.snackbar.Snackbar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.permission.PermissionRequester
import org.jetbrains.anko.longToast

class DownloadIntoInternalStorageProcess(private val post: Post, private val booru: Booru) {

    fun start(context: Context, permissionRequester: PermissionRequester) {
        //show notification that the loading was started
        NotificationProcess(post).start(context) {
            setProgress(1, 1, true)
        }
        context.longToast(context.getString(R.string.download_was_started)).show()
        permissionRequester.requestPermission(permission) {
            doOnPermissionRequestResult(context, it)
        }
    }

    private fun doOnPermissionRequestResult(context: Context, granted: Boolean) =
        if (granted) onPermissionGranted(context) else onPermissionDenied(context)

    private fun onPermissionGranted(context: Context) {
        DownloadProcess(booru, post).start(context) { downloadedData, throwable ->
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
        if (context is Activity) {
            val view = context.window.decorView
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
        } else {
            context.longToast(message).show()
        }
    }

    companion object {
        private const val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    }
}