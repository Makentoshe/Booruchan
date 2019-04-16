package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.component.tag.TagFactory

class Rule34TagFactory : TagFactory<Rule34Tag> {

    override fun build(attributes: Map<String, String>, action: Rule34Tag.() -> Unit): Rule34Tag {
        return Rule34Tag(attributes).apply(action)
    }
}