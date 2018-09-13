package com.makentoshe.booruchan.common.api.factory

import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru

class GelbooruFactory: Factory {

    override fun createService(): Boor {
        return Gelbooru()
    }
}