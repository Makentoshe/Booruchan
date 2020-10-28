package com.makentoshe.booruchan.core.post

import java.io.File

/** Base image interface provides [url], it's [width] and [height], and [extension] */
interface Image {
    val extension: String
    val url: String
    val height: Int?
    val width: Int?
}

/** Typing for [Image] class for full sized images */
interface FullImage : Image

fun fullImage(url: String, height: Int? = null, width: Int? = null) = object : FullImage {
    override val url = url
    override val height = height
    override val width = width
    override val extension = File(url).extension
}

/** Typing for [Image] class for preview images */
interface PreviewImage : Image

fun previewImage(url: String, height: Int? = null, width: Int? = null) = object : PreviewImage {
    override val url = url
    override val height = height
    override val width = width
    override val extension = File(url).extension
}

/** Typing for [Image] class for images with sample size*/
interface SampleImage : Image

fun sampleImage(url: String, height: Int? = null, width: Int? = null) = object : SampleImage {
    override val url = url
    override val height = height
    override val width = width
    override val extension = File(url).extension
}
