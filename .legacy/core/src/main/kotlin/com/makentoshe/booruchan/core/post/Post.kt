package com.makentoshe.booruchan.core.post

import com.makentoshe.booruchan.core.Time
import java.io.Serializable

interface Post : PostId, Serializable {
    val creationTime: Time

    val score: Score
    val rating: Rating
    val tags: Tags

    val fullContent: FullContent
    val sampleContent: SampleContent
    val previewContent: PreviewContent

    /** Nullable because if source can be not defined by default */
    val source: String?

    /** Ratio of height to width */
    val htwRatio: Float
}
