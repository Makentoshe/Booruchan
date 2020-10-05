package post.network

import network.Filter

abstract class PostsFilter(protected val params: Map<String, Any>) : Filter {

    override fun toUrl(): String {
        if (params.isEmpty()) return ""
        return params.entries.mapIndexed { index, entry ->
            val builder = StringBuilder()
            if (index == 0) builder.append("?") else builder.append("&")
            builder.append(entry.key).append("=").append(entry.value)
        }.joinToString { it.toString() }
    }
}