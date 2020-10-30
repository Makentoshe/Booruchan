package com.makentoshe.booruchan.core.post.network

import com.makentoshe.booruchan.core.network.Filter

abstract class PostsFilter(protected val params: Map<String, Any>) : Filter {

    protected abstract val firstChar: String

    override fun toUrl(): String {
        if (params.isEmpty()) return ""
        return params.entries.mapIndexed { index, entry ->
            "${if (index == 0) firstChar else "&"}${entry.key}=${entry.value}"
        }.joinToString("")
    }
}