package comment.network

data class DanbooruCommentsFilter(val count: Int?) {

    private val params = HashMap<String, Any>()

    init {
        // required param else the posts will be returned.
        params["group_by"] = "comment"

        if (count != null) params[COUNT] = count
    }

    override fun toString(): String {
        if (params.isEmpty()) return ""
        return params.entries.mapIndexed { index, entry ->
            val builder = StringBuilder()
            if (index == 0) builder.append("?") else builder.append("&")
            builder.append(entry.key).append("=").append(entry.value)
        }.joinToString { it.toString() }
    }

    companion object {
        private const val COUNT = "limit"
    }
}
