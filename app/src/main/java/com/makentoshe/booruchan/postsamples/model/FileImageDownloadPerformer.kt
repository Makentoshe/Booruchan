package com.makentoshe.booruchan.postsamples.model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import androidx.core.content.ContextCompat
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.R
import java.io.File
import java.io.FileOutputStream

class FileImageDownloadPerformer(
    private val booru: Booru,
    private val confirmFileDownloadController: ConfirmFileDownloadController
) {

    fun perform(post: Post, byteArray: ByteArray, context: Context) {
        if (!isWriteStoragePermissionGranted(context)) {
            return confirmFileDownloadController.action(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        val imageDir = getImageDirectory(context)
        val title = post.id.toString()
        val extension = File(post.fileUrl).extension
        val imagefile = File(imageDir, "$title.$extension")
        imagefile.createNewFile()

        FileOutputStream(imagefile).use { fos ->
            fos.write(byteArray)
            fos.flush()
            confirmFileDownloadController.action("$title.$extension")
        }
    }

    private fun isWriteStoragePermissionGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getImageDirectory(context: Context): File {
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val appDir = context.getString(R.string.app_name)
        val imageDir = File(root, "${File.separator}$appDir${File.separator}${booru.title}")
        return imageDir.apply { mkdirs() }
    }
}