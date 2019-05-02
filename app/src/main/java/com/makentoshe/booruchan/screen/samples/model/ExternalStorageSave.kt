package com.makentoshe.booruchan.screen.samples.model

import android.content.Context
import android.os.Environment
import com.makentoshe.booruchan.R
import java.io.File
import java.io.FileOutputStream

class ExternalStorageSave(private val context: Context) {

    /* Saved the data to the internal storage */
    fun start(data: DownloadedData) {
        //create image file
        val imageDir = getImageDirectory(context, data.booruTitle)
        val imagefile = File(imageDir, "${data.title}.${data.extension}")
        imagefile.createNewFile()
        //write to it
        FileOutputStream(imagefile).use { fos ->
            fos.write(data.byteArray)
            fos.flush()
        }
    }

    companion object {
        /* Returns the calculated image directory based on booru title */
        fun getImageDirectory(context: Context, title: String): File {
            val root =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val appDir = context.getString(R.string.app_name)
            val imageDir = File(root, "${File.separator}$appDir${File.separator}$title")
            return imageDir.apply { mkdirs() }
        }
    }
}