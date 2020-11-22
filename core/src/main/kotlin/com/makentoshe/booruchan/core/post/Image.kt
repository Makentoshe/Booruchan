package com.makentoshe.booruchan.core.post

import java.io.File
import java.io.Serializable

/** Base image interface provides [url], it's [width] and [height], [name] and [extension] */
interface Image: Serializable {
    val extension: String
    val url: String
    val height: Int?
    val width: Int?
    val name: String
}

/** Typing for [Image] class for full sized images */
interface FullImage : Image, Serializable

fun fullImage(
    url: String,
    height: Int? = null,
    width: Int? = null,
    extension: String = File(url).extension,
    name: String = File(url).name
) = object : FullImage {
    override val url = url
    override val height = height
    override val width = width
    override val extension = extension
    override val name = name
}

/** Typing for [Image] class for preview images */
interface PreviewImage : Image, Serializable

fun previewImage(
    url: String,
    height: Int? = null,
    width: Int? = null,
    extension: String = File(url).extension,
    name: String = File(url).name
) = object : PreviewImage {
    override val url = url
    override val height = height
    override val width = width
    override val extension = extension
    override val name = name
}

/** Typing for [Image] class for images with sample size*/
interface SampleImage : Image, Serializable

fun sampleImage(
    url: String,
    height: Int? = null,
    width: Int? = null,
    extension: String = File(url).extension,
    name: String = File(url).name
) = object : SampleImage {
    override val url = url
    override val height = height
    override val width = width
    override val extension = extension
    override val name = name
}
