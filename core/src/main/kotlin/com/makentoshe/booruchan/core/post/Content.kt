package com.makentoshe.booruchan.core.post

import java.io.File
import java.io.Serializable

/** Base content interface provides [url], it's [width] and [height], [name] and [extension] */
interface Content: Serializable {
    val extension: String
    val url: String
    val height: Int?
    val width: Int?
    val name: String
}

/** Typing for [Content] class for full sized images */
interface FullContent : Content, Serializable

fun fullContent(
    url: String,
    height: Int? = null,
    width: Int? = null,
    extension: String = File(url).extension,
    name: String = File(url).name
) = object : FullContent {
    override val url = url
    override val height = height
    override val width = width
    override val extension = extension
    override val name = name
}

/** Typing for [Content] class for preview images */
interface PreviewContent : Content, Serializable

fun previewContent(
    url: String,
    height: Int? = null,
    width: Int? = null,
    extension: String = File(url).extension,
    name: String = File(url).name
) = object : PreviewContent {
    override val url = url
    override val height = height
    override val width = width
    override val extension = extension
    override val name = name
}

/** Typing for [Content] class for images with sample size*/
interface SampleContent : Content, Serializable

fun sampleContent(
    url: String,
    height: Int? = null,
    width: Int? = null,
    extension: String = File(url).extension,
    name: String = File(url).name
) = object : SampleContent {
    override val url = url
    override val height = height
    override val width = width
    override val extension = extension
    override val name = name
}
