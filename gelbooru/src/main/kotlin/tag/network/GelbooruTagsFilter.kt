package tag.network

class GelbooruTagsFilter(params: Map<String, Any>) : TagsFilter(params) {

    constructor(count: Int?) : this(buildMap(count))

    override fun toUrl(): String {
        if (params.isEmpty()) return ""
        return params.entries.map { entry -> "&${entry.key}=${entry.value}" }.joinToString("") { it }
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
