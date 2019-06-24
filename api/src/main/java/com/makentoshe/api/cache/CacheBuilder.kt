package com.makentoshe.api.cache

import android.content.Context

/** Class performs a building any types of cache */
class CacheBuilder(context: Context) {

    /** Path for a posts cache directory */
    private val postsDir = PostDiskCache.getDir(context)

    /** Path for a preview image cache directory */
    private val previewDir = ImageDiskCache.getPreviewDir(context)

    /** Path for a sample image cache directory */
    private val sampleDir = ImageDiskCache.getSampleDir(context)

    /** Path for a full size image cache directory */
    private val fileDir = ImageDiskCache.getFileDir(context)

    /** Factory method builds and returns an instance of a Cache object for a preview images */
    fun buildPreviewCache() = ImageDiskCache(previewDir)

    /** Factory method builds and returns an instance of a Cache object for a sample images */
    fun buildSampleCache() = ImageDiskCache(sampleDir)

    /** Factory method builds and returns an instance of a Cache object for a full size images */
    fun buildFileCache() = ImageDiskCache(fileDir)

    /** Factory method builds and returns an instance of a Cache object for a full size images */
    fun buildPostsCache() = PostDiskCache(postsDir)
}