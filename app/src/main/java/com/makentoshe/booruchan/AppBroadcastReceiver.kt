package com.makentoshe.booruchan

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

class AppBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val imageType = "image/*"
        const val videoType = "video/"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent == null) return

        if (intent.hasExtra(imageType)) {
            startDefaultImageGalleryApp(context, intent.getStringExtra(imageType))
        }

        if (intent.hasExtra(videoType)) {
            startDefaultVideoGalleryApp(context, intent.getStringExtra(videoType))
        }
    }

    private fun startDefaultImageGalleryApp(context: Context, suri: String) {
        val uri = context.convertStringToUri(suri)
        val intent = createIntent()
        intent.setDataAndType(uri, imageType)
        context.startActivity(Intent.createChooser(intent, "Open file"))
    }

    private fun startDefaultVideoGalleryApp(context: Context, suri: String) {
        val uri = context.convertStringToUri(suri)
        val intent = createIntent()
        intent.setDataAndType(uri, videoType)
        context.startActivity(Intent.createChooser(intent, "Open file"))
    }

    private fun Context.convertStringToUri(suri: String): Uri {
        val uri = FileProvider.getUriForFile(this, packageName.plus(".appfileprovider"), File(suri))
        grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        return uri
    }

    private fun createIntent() = Intent(Intent.ACTION_VIEW).apply {
        flags = FLAG_GRANT_READ_URI_PERMISSION
    }
}
