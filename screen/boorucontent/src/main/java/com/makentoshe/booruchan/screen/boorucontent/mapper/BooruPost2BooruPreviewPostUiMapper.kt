package com.makentoshe.booruchan.screen.boorucontent.mapper

import com.makentoshe.booruchan.feature.post.BooruPost
import com.makentoshe.booruchan.screen.boorucontent.domain.BooruPreviewPostUi
import javax.inject.Inject

class BooruPost2BooruPreviewPostUiMapper @Inject constructor() {
    fun map(booruPost: BooruPost) = BooruPreviewPostUi(
        id = booruPost.id.int,
        url = booruPost.preview.url,
        hwRatio = booruPost.preview.size.hwRatio,
    )
}
