package com.makentoshe.booruchan.screen.samples

import android.Manifest
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.permission.PermissionRequester
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.longToast
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class DownloadIntoInternalStorage(private val post: Post, private val booruTitle: String) {

    fun perform(context: Context, permissionRequester: PermissionRequester) {
        permissionRequester.requestPermission(permission) {
            if (it) {
                val future = Glide.with(context).asBitmap().load(post.fileUrl).submit()
                GlobalScope.launch {
                    try {
                        val stream = ByteArrayOutputStream()
                        if (!future.get().compress(Bitmap.CompressFormat.PNG, 100, stream)) {
                            //error while compressing
                            return@launch
                        }
                        val ext = File(post.fileUrl).extension
                        val name = post.id.toString()
                        saveFile(context, booruTitle, name, ext, stream.toByteArray())
                        fileWasSuccessfullyLoaded(context)
                    } catch (e: Exception) {
                        fileWasUnsuccessfullyLoaded(context, e.localizedMessage)
                    }
                }
            } else {
                permissionDeniedCallback(context)
            }
        }
    }

    /* Returns the calculated image directory based on booru title */
    private fun getImageDirectory(title: String, context: Context): File {
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val appDir = context.getString(R.string.app_name)
        val imageDir = File(root, "${File.separator}$appDir${File.separator}$title")
        return imageDir.apply { mkdirs() }
    }

    /* Saves a byte array into the file with the given name and extension parameters */
    private fun saveFile(context: Context, dirTitle: String, name: String, ext: String, byteArray: ByteArray) {
        val imageDir = getImageDirectory(dirTitle, context)
        val imagefile = File(imageDir, "$name.$ext")
        imagefile.createNewFile()

        FileOutputStream(imagefile).use { fos ->
            fos.write(byteArray)
            fos.flush()
        }
    }

    /* Calls when file was successfully downloaded */
    private fun fileWasSuccessfullyLoaded(context: Context) {
        val message = StringBuilder(context.getString(R.string.file)).append(" ")
        message.append(context.getString(R.string.was_loaded_succesfully))
        sendNotificationWithMessage(context, message)
    }

    /* Calls when error occurs while file was downloading */
    private fun fileWasUnsuccessfullyLoaded(context: Context, reason: String) {
        val message = StringBuilder(context.getString(R.string.image_was_not_loaded_succesfully))
        if (!reason.isBlank()) message.append("\n").append(reason)
        sendNotificationWithMessage(context, message)
    }

    /* Calls when write external storage permission was denied */
    private fun permissionDeniedCallback(context: Context) {
        val message = StringBuilder(context.getString(R.string.download_was_not_started))
            .append("\n").append(context.getString(R.string.permission_denied))
        sendNotificationWithMessage(context, message)
    }


    private fun sendNotificationWithMessage(context: Context, message: CharSequence) {
        if (context is Activity) {
            val view = context.window.decorView
            Snackbar.make(
                view,
                message,
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            context.longToast(message).show()
        }
    }

    companion object {
        private const val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    }
}