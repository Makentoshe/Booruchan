package com.makentoshe.booruchan.screen.samples.model

import android.Manifest
import android.app.Activity
import android.content.Context
import com.google.android.material.snackbar.Snackbar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.screen.samples.model.*
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.longToast
import org.koin.core.KoinComponent
import java.lang.Exception

class ExternalStorageDownloader(
    private val post: Post,
    private val booru: Booru,
    private val rxPermissions: RxPermissions
) : KoinComponent {

    private val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE

    fun start(context: Context) {
        //request write permission
        if (rxPermissions.isGranted(permission)) {
            onPermissionGranted(context)
        } else {
            var disposable: Disposable? = null
            disposable = rxPermissions.request(permission).subscribe { granted ->
                onRequestPermissionResult(context, granted)
                disposable?.dispose()
            }
        }
    }

    private fun onRequestPermissionResult(context: Context, granted: Boolean) {
        if (granted) onPermissionGranted(context) else onPermissionDenied(context)
    }

    private fun onPermissionGranted(context: Context) {

        context.longToast(context.getString(R.string.download_was_started)).show()

        //show notification that the loading was started
        NotificationProcess(post).start(context) {
            setProgress(1, 1, true)
            setContentText(context.getString(R.string.downloading))
        }

        performDownload(context)
    }

    private fun onPermissionDenied(context: Context) {
        val message = StringBuilder(context.getString(R.string.download_was_not_started))
            .append("\n").append(context.getString(R.string.permission_denied))

        if (context is Activity) {
            val view = context.window.decorView
            Snackbar
                .make(view, message, Snackbar.LENGTH_LONG).show()
        } else {
            context.longToast(message).show()
        }
    }

    private fun performDownload(context: Context) {
        ExternalStorageDownload(booru, post)
            .start(context) { downloadedData, throwable ->
            onDownloaded(context, downloadedData, throwable)
        }
    }

    private fun onDownloaded(context: Context, downloadedData: DownloadedData?, throwable: Throwable?) {
        if (throwable == null) {
            try {
                ExternalStorageSave(context).start(downloadedData!!)
                onSuccess(context, downloadedData)
            } catch (e: Exception) {
                onError(context, e)
            }
        } else {
            onError(context, throwable)
        }
    }

    /* Calls when file was successfully downloaded and displays a notification message */
    private fun onSuccess(context: Context, downloadedData: DownloadedData) {
        NotificationSuccessProcess(
            downloadedData,
            NotificationProcess(post)).start(context)
    }

    /* Calls when error occurs while file was downloading */
    private fun onError(context: Context, throwable: Throwable) {
        NotificationUnsuccessProcess(
            throwable,
            post.id.toInt(),
            NotificationProcess(post)).start(context)
    }
}