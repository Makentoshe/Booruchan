package com.makentoshe.booruchan.application.android.database.record

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makentoshe.booruchan.core.post.Content
import com.makentoshe.booruchan.core.post.previewContent

/** Contains preview content information in database for performing simple caching in file storage */
@Entity
data class PreviewContentRecord(
    /** Contains relative location in file cache */
    val location: String,

    val extension: String,

    val url: String,

    val height: Int?,

    val width: Int?,

    val name: String
) {

    /** Contains record position and used in cache directly */
    @PrimaryKey(autoGenerate = true)
    var history: Long = 0L

    constructor(location: String, content: Content) : this(
        location, content.extension, content.url, content.height, content.width, content.name
    )

    fun toContent() = previewContent(url, height, width, extension, name)
}