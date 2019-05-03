package com.makentoshe.booruchan.model

import com.makentoshe.booruchan.api.Booru

interface BooruHolder {
    val booru: Booru
}

class BooruHolderImpl(private val _booru: Booru) : BooruHolder {
    override val booru: Booru
        get() = _booru
}
