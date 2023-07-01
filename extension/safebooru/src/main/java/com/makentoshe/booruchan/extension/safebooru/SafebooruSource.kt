package com.makentoshe.booruchan.extension.safebooru

import com.makentoshe.booruchan.extension.BooruContext
import com.makentoshe.booruchan.extension.BooruSource
import com.makentoshe.booruchan.feature.context.BooruSystem

class SafebooruSource : BooruSource {
    override val context = BooruContext(
        title = "Safebooru",
        system = BooruSystem.Gelbooru020System,
        host = "https://safebooru.org",
    )
}
