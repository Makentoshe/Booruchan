package com.makentoshe.booruchan.posttags.model

import com.makentoshe.booruapi.Tag
import com.makentoshe.controllers.DownloadResult

interface TagsBuildController {
    fun onTagsReceivedListener(action: (DownloadResult<Set<Tag>>) -> Unit)
}