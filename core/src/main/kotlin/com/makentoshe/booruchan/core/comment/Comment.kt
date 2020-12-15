package com.makentoshe.booruchan.core.comment

import com.makentoshe.booruchan.core.CreatorId
import com.makentoshe.booruchan.core.Text
import com.makentoshe.booruchan.core.Time
import com.makentoshe.booruchan.core.post.PostId
import java.io.Serializable

interface Comment : CommentId, Text, CreatorId, PostId, Serializable {
    val creationTime: Time
}