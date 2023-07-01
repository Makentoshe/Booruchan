package com.makentoshe.booruchan.extension.gelbooru

import com.makentoshe.booruchan.extension.BooruContext
import com.makentoshe.booruchan.extension.BooruSource
import com.makentoshe.booruchan.feature.context.BooruSystem

class GelbooruSource : BooruSource {

    override val context = BooruContext(
        title = "Gelbooru",
        system = BooruSystem.Gelbooru025System,
        host = "https://gelbooru.com"
    )

}