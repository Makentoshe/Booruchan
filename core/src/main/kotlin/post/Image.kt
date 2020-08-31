package post

/** Base image with interface provides [url], it's [width] and [height], and [extension] */
interface Image {
    val extension: String
    val url: String
    val height: Int?
    val width: Int?
}

/** Typing for [Image] class for full sized images */
interface FullImage : Image

/** Typing for [Image] class for preview images */
interface PreviewImage : Image

/** Typing for [Image] class for images with sample size*/
interface SampleImage : Image
