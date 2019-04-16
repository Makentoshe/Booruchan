package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.component.tag.TagFactory

class GelbooruTagFactory : TagFactory<GelbooruTag> {

    override fun build(attributes: Map<String, String>, action: GelbooruTag.() -> Unit): GelbooruTag {
        return GelbooruTag(attributes).apply(action)
    }
}