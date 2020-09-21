package tag.network

data class GelbooruTagsFilter(val count: Int?) {

    private val params = HashMap<String, Any>()

    init {
        if (count != null) params[COUNT] = count
    }

    fun toUrl(): String {
        if (params.isEmpty()) return ""
        return params.entries.joinToString("") { entry -> "&${entry.key}=${entry.value}" }
    }

    companion object {
        private const val COUNT = "limit"
    }
}