package com.makentoshe.booruchan.api.component.tag


interface TagFactory<T : Tag> {

    fun build(attributes: Map<String, String>, action: T.() -> Unit = {}): T

    fun build(vararg pairs: Pair<String, String>, action: T.() -> Unit = {}): T {
        return build(pairs.toMap(), action)
    }
}