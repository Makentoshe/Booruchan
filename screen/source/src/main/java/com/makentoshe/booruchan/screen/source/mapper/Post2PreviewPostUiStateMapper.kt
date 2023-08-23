package com.makentoshe.booruchan.screen.source.mapper

import com.makentoshe.booruchan.feature.entity.Post
import com.makentoshe.booruchan.screen.source.entity.PreviewPostUiState
import javax.inject.Inject

class Post2PreviewPostUiStateMapper @Inject constructor() {

    fun map(post: Post) = PreviewPostUiState(
        id = post.id,
        url = post.previewImageUrl,
        hwRatio = calculateHwRatio(height = post.previewImageHeight, width = post.previewImageWidth),
    )

    private fun calculateHwRatio(height: Int, width: Int): Float {
        if (height == 0 || width == 0) return 1.0f

        return height.toFloat() / width.toFloat()
    }
}