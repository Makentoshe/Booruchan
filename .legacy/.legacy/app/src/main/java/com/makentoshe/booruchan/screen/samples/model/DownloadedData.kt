package com.makentoshe.booruchan.screen.samples.model

data class DownloadedData(
    val byteArray: ByteArray,
    val title: String,
    val extension: String,
    val booruTitle: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DownloadedData

        if (!byteArray.contentEquals(other.byteArray)) return false
        if (title != other.title) return false
        if (extension != other.extension) return false
        if (booruTitle != other.booruTitle) return false

        return true
    }

    override fun hashCode(): Int {
        var result = byteArray.contentHashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + extension.hashCode()
        result = 31 * result + booruTitle.hashCode()
        return result
    }
}