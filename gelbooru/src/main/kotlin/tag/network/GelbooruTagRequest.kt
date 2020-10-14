package tag.network

import network.GelbooruRequest

sealed class GelbooruTagRequest : GelbooruRequest(), TagRequest {
    protected val internalUrl = "$host/index.php?page=dapi&s=tag&q=index"
}

data class XmlGelbooruTagRequest(private val filter: GelbooruTagFilter) : GelbooruTagRequest() {
    override val url = when (filter) {
        is GelbooruTagFilter.ById -> "$internalUrl&id=${filter.id.tagId}"
        is GelbooruTagFilter.ByName -> "$internalUrl&name=${filter.name}"
    }
}

data class JsonGelbooruTagRequest(private val filter: GelbooruTagFilter) : GelbooruTagRequest() {
    override val url = when (filter) {
        is GelbooruTagFilter.ById -> "$internalUrl&id=${filter.id.tagId}&json=1"
        is GelbooruTagFilter.ByName -> "$internalUrl&name=${filter.name}&json=1"
    }
}
