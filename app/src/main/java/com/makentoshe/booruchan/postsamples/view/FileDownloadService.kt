package com.makentoshe.booruchan.postsamples.view

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import android.widget.Toast
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamples.ArgumentsHolder
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.Repository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * Service for downloading file into the external storage
 */
class FileDownloadService : Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //get arguments
        val position = intent.getIntExtra(POSITION, 0)
        val args =
            ArgumentsHolder[FileDownloadService::class.java.simpleName.plus(
                position
            )]!!

        val postsRepository = args.getSerializable(POSTSREP) as PostsRepository
        val filesRepository =
            args.getSerializable(FILESREP) as Repository<String, ByteArray>
        val permissionChecker =
            args.getSerializable(PERMCHCK) as PermissionChecker
        ArgumentsHolder.remove(
            FileDownloadService::class.java.simpleName.plus(
                position
            )
        )

        //start saving
        var disposable: Disposable? = null
        try {
            val post = performPostLoading(position, postsRepository)
            val fileAsBytes = performByteArrayLoading(post, filesRepository)
            disposable = permissionChecker.requestPermisson(Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                if (it) {
                    val postfile = File(post.fileUrl)
                    saveFile(baseContext, "BooruTitle", postfile.name, postfile.extension, fileAsBytes)
                    finish("Cool")
                } else {
                    finish("Not cool")
                }
            }
        } catch (e: Exception) {
            finish(e.localizedMessage)
        } finally {
            disposable?.dispose()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    //calls on the saving finish
    private fun finish(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
        stopSelf()
    }

    /* Returns post file from the posts repository */
    private fun performPostLoading(position: Int, postsRepository: PostsRepository): Post {
        val posts = postsRepository.get(position / postsRepository.count)
        return posts[position % postsRepository.count]
    }

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

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        private const val POSITION = "Position"
        private const val POSTSREP = "PostsRepository"
        private const val FILESREP = "RilesRepository"
        private const val PERMCHCK = "PermissionChecker"

        fun startService(
            context: Context,
            position: Int,
            postsRepository: PostsRepository,
            fileRepository: Repository<String, ByteArray>,
            permissionChecker: PermissionChecker
        ) {
            val mainArg = Intent(
                context,
                FileDownloadService::class.java
            ).apply {
                putExtra(POSITION, position)
            }

            val args = Bundle().apply {
                putSerializable(POSTSREP, postsRepository)
                putSerializable(FILESREP, fileRepository)
                putInt(POSITION, position)
                putSerializable(PERMCHCK, permissionChecker)
            }

            ArgumentsHolder[FileDownloadService::class.java.simpleName.plus(position)] = args

            context.startService(mainArg)
        }

    }
}