package com.makentoshe.booruchan.postsamples.view

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.NotificationInterface
import com.makentoshe.booruchan.SnackbarNotificationRxController
import com.makentoshe.booruchan.postsamples.ArgumentsHolder
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.Repository
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * Service for downloading file into the external storage
 */
class FileDownloadService : Service(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int = runBlocking {
        //get arguments
        val position = intent.getIntExtra(POSITION, 0)
        val args = ArgumentsHolder[FileDownloadService::class.java.simpleName.plus(position)]!!
        val postsRepository = args.getSerializable(POSTSREP) as PostsRepository
        val filesRepository = args.getSerializable(FILESREP) as Repository<String, ByteArray>
        val permissionChecker = args.getSerializable(PERMCHCK) as PermissionChecker
        val snackbarNotificatorController = args.getSerializable(SNACKNOT) as NotificationInterface

        ArgumentsHolder.remove(FileDownloadService::class.java.simpleName.plus(position))

        //start loading and saving

        var disposable: Disposable? = null
        try {
            val post = Post()//performPostLoading(position, postsRepository)
            val fileAsBytes = performByteArrayLoading(post, filesRepository)
            disposable = permissionChecker.requestPermisson(Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                try {
                    if (it) {
                        val postfile = File(post.fileUrl)
                        saveFile(baseContext, "BooruTitle", post.id.toString(), postfile.extension, fileAsBytes)
                        imageWasLoadedMessage(post.id.toString(), postfile.extension, snackbarNotificatorController)
                    } else {
                        val permissionDenied = baseContext.getString(R.string.permission_denied)
                        imageWasNotLoadedMessage(snackbarNotificatorController, permissionDenied)
                    }
                } finally {
                    disposable?.dispose()
                }
            }
        } catch (e: Exception) {
            disposable?.dispose()
            imageWasNotLoadedMessage(snackbarNotificatorController, e.localizedMessage)
        }

        return@runBlocking super.onStartCommand(intent, flags, startId)
    }

    /**
     * Calls when the post image was successfully downloaded.
     *
     * @param name the downloaded image file name.
     * @param ext the downloaded image file extension.
     * @param controller the controller for the notifying.
     */
    private fun imageWasLoadedMessage(name: String, ext: String, controller: NotificationInterface) {
        val message = StringBuilder(baseContext.getString(R.string.image)).append(" ")
        message.append(name).append(".").append(ext).append(" ")
        message.append(baseContext.getString(R.string.was_loaded_succesfully))
        val notifyMessage = SnackbarNotificationRxController.NotificationMessage(message.toString())
        controller.notify(notifyMessage)
        stopSelf()
    }

    /**
     * Calls when the post image downloading failed.
     *
     * @param controller the controller for the notifying.
     * @param additionMessage additional message will be displayed below,
     */
    private fun imageWasNotLoadedMessage(controller: NotificationInterface, additionMessage: String = "") {
        val message = StringBuilder(baseContext.getString(R.string.image_was_not_loaded_succesfully))
        if (!additionMessage.isBlank()) message.append("\n").append(additionMessage)
        val notifyMessage = SnackbarNotificationRxController.NotificationMessage(message.toString())
        controller.notify(notifyMessage)
        stopSelf()
    }

    /* Returns post file from the posts repository */
//    private fun performPostLoading(position: Int, postsRepository: PostsRepository): Post {
//        val posts = postsRepository.get(position / postsRepository.count)
//        return posts[position % postsRepository.count]
//    }

    /* Returns array of a bytes from the files repository */
    private fun performByteArrayLoading(post: Post, fileRepository: Repository<String, ByteArray>): ByteArray {
        return fileRepository.get(post.sampleUrl)!!
    }

    /* Returns the calculated image directory based on booru title */
    private fun getImageDirectory(title: String, context: Context): File {
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val appDir = context.getString(R.string.app_name)
        val imageDir = File(root, "${File.separator}$appDir${File.separator}$title")
        return imageDir.apply { mkdirs() }
    }

    /* Saves a bytearray into the file with the given name and extension parameters */
    private fun saveFile(context: Context, dirTitle: String, name: String, ext: String, byteArray: ByteArray) {
        val imageDir = getImageDirectory(dirTitle, context)
        val imagefile = File(imageDir, "$name.$ext")
        imagefile.createNewFile()

        FileOutputStream(imagefile).use { fos ->
            fos.write(byteArray)
            fos.flush()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        private const val POSITION = "Position"
        private const val POSTSREP = "PostsRepository"
        private const val FILESREP = "RilesRepository"
        private const val PERMCHCK = "PermissionChecker"
        private const val SNACKNOT = "SnackbarNotificator"

        fun startService(
            context: Context,
            position: Int,
            postsRepository: PostsRepository,
            fileRepository: Repository<String, ByteArray>,
            permissionChecker: PermissionChecker,
            notificationController: NotificationInterface
        ) {
            val mainArg = Intent(
                context,
                FileDownloadService::class.java
            ).apply {
                putExtra(POSITION, position)
            }

            val args = Bundle().apply {
                putInt(POSITION, position)
                putSerializable(POSTSREP, postsRepository)
                putSerializable(FILESREP, fileRepository)
                putSerializable(PERMCHCK, permissionChecker)
                putSerializable(SNACKNOT, notificationController)
            }

            ArgumentsHolder[FileDownloadService::class.java.simpleName.plus(position)] = args

            context.startService(mainArg)
        }

    }
}