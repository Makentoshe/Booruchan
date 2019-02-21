package com.makentoshe.booruchan.postsamples

import android.Manifest
import android.content.Context
import android.os.Environment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.NotificationInterface
import com.makentoshe.booruchan.PostInternalCache
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.SnackbarNotificationRxController
import com.makentoshe.booruchan.postsamples.model.DownloadFileController
import com.makentoshe.booruchan.postsamples.view.PermissionChecker
import com.makentoshe.repository.CachedRepository
import com.makentoshe.repository.FileImageRepository
import com.makentoshe.repository.PostsRepository
import com.makentoshe.viewmodel.ViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class DownloadFileViewModel private constructor() : ViewModel(), DownloadFileController {
    /* Set of the selected tags for posts searching while file downloading but
     * in real situation it is not necessary - something must go very wrong if they will be needed */
    private lateinit var tags: Set<Tag>
    /* Current Booru instance for creating repositories */
    private lateinit var booru: Booru
    /* Controller for checking and requesting application permissions */
    private lateinit var permissionChecker: PermissionChecker
    /* Controller for displaying snackbar notifications */
    private lateinit var notificationController: NotificationInterface

    override fun startDownload(view: View, currentItem: Int) {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val context = view.context
        permissionChecker.requestPermisson(permission) {
            if (it) {
                try {
                    startFileLoading(context, currentItem)
                    fileWasSuccessfullyLoaded(context)
                } catch (e: Exception) {
                    fileWasUnsuccessfullyLoaded(context, e.localizedMessage)
                }
            } else {
                permissionDeniedCallback(context)
            }
        }
    }

    /* Calls when write external storage permission was denied */
    private fun permissionDeniedCallback(context: Context) {
        val sb = StringBuilder(context.getString(R.string.download_was_not_started))
            .append("\n").append(context.getString(R.string.permission_denied))
        val notify = SnackbarNotificationRxController.NotificationMessage(sb.toString())
        notificationController.notify(notify)
    }

    /* Calls when file was successfully downloaded */
    private fun fileWasSuccessfullyLoaded(context: Context) {
        val message = StringBuilder(context.getString(R.string.file)).append(" ")
        message.append(context.getString(R.string.was_loaded_succesfully))
        val notifyMessage =
            SnackbarNotificationRxController.NotificationMessage(message.toString())
        notificationController.notify(notifyMessage)
    }

    /* Calls when error occurs while file was downloading */
    private fun fileWasUnsuccessfullyLoaded(context: Context, reason: String) {
        val message = StringBuilder(context.getString(R.string.image_was_not_loaded_succesfully))
        if (!reason.isBlank()) message.append("\n").append(reason)
        val notifyMessage =
            SnackbarNotificationRxController.NotificationMessage(message.toString())
        notificationController.notify(notifyMessage)
    }

    /* Perform a file downloading */
    private fun startFileLoading(context: Context, position: Int) = launch {
        val postsRepository = CachedRepository(
            PostInternalCache(context, "posts"),
            PostsRepository(booru)
        )
        val filesRepository = FileImageRepository(booru)

        val post = postsRepository.get(Booru.PostRequest(1, position, tags))!![0]
        val bytes = filesRepository.get(post.fileUrl)

        saveFile(context, booru.title, post.id.toString(), File(post.fileUrl).extension, bytes)
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

    class Factory(
        private val booru: Booru,
        private val tags: Set<Tag>,
        private val permissionChecker: PermissionChecker,
        private val notificationController: NotificationInterface
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = DownloadFileViewModel()
            viewModel.booru = booru
            viewModel.permissionChecker = permissionChecker
            viewModel.notificationController = notificationController
            viewModel.tags = tags
            return viewModel as T
        }
    }
}