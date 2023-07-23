package com.makentoshe.booruchan

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.makentoshe.booruchan.library.logging.initializeDebugLogger
import dagger.hilt.android.HiltAndroidApp
import java.io.File

@HiltAndroidApp
class Booruchan : Application(), ImageLoaderFactory {

    private val diskCacheDirectory: File
        get() = cacheDir.resolve("image_cache")

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            initializeDebugLogger()
        }
    }

    override fun newImageLoader() = ImageLoader.Builder(this)
        .memoryCache { newImageLoaderMemoryCache() }
        .diskCache { newImageLoaderDiskCache() }
        .build()

    private fun newImageLoaderMemoryCache(): MemoryCache {
        return MemoryCache.Builder(this).maxSizePercent(0.25).build()
    }

    private fun newImageLoaderDiskCache(): DiskCache {
        return DiskCache.Builder().directory(diskCacheDirectory).maxSizePercent(0.02).build()
    }

}