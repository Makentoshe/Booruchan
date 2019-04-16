package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.component.tag.TagFactory

class SafebooruTagFactory : TagFactory<SafebooruTag> {
    override fun build(attributes: Map<String, String>, action: SafebooruTag.() -> Unit): SafebooruTag {
        return SafebooruTag(attributes).apply(action)
    }
}