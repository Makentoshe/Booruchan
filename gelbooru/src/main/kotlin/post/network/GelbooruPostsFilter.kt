package post.network

class GelbooruPostsFilter(
    params: Map<String, Any>
) : PostsFilter(params) {

    constructor(count: Int? = null) : this(buildMap(count))

    override fun toUrl(): String {
        if (params.isEmpty()) return ""
        return params.entries.map { entry ->
            val builder = StringBuilder().append("&")
            builder.append(entry.key).append("=").append(entry.value)
        }.joinToString("") { it.toString() }
    }

    companion object {
        private const val COUNT = "limit"

        private fun buildMap(count: Int?): Map<String, Any> {
            val params = HashMap<String, Any>()
            if (count != null) params[COUNT] = count
            return params
        }
    }
}
