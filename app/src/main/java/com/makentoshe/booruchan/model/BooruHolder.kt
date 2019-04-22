package com.makentoshe.booruchan.model

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag

interface BooruHolder {
    val booru: Booru
}

interface TagsHolder {
    val tags: Set<Tag>
}
