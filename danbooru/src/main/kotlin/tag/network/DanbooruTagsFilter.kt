package tag.network

data class DanbooruTagsFilter(val count: Int?) {
    override fun toString(): String {
        return "?limit=$count"
    }
}