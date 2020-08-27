package post

/** Base image with interface provides [url], it's [width] and [height], and [extension] */
interface Image {
    val extension: String
    val url: String
    val height: Int
    val width: Int
}