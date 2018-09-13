package com.makentoshe.booruchan.start.model.api.factory

import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru

class GelbooruFactory: Factory {

    override fun createServiceInstance(): Boor {
        return Gelbooru()
    }
}