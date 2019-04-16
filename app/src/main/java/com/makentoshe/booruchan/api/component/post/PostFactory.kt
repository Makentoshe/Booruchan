package com.makentoshe.booruchan.api.component.post

interface PostFactory<T : Post> {
    fun build(attributes: Map<String, String>, action: T.() -> Unit = {}): T

    fun build(vararg pairs: Pair<String, String>, action: T.() -> Unit = {}): T {
        val map = HashMap<String, String>()
        pairs.forEach { map[it.first] = it.second }
        return build(map, action)
    }
}