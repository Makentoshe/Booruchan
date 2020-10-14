package tag.network

import network.GelbooruRequest

sealed class GelbooruTagsRequest : GelbooruRequest(), TagsRequest {
    protected val internalUrl = "$host/index.php?page=dapi&s=tag&q=index"
}

data class XmlGelbooruTagsRequest(private val filter: GelbooruTagsFilter) : GelbooruTagsRequest() {
    override val url = "$internalUrl${filter.toUrl()}"
}

data class JsonGelbooruTagsRequest(private val filter: GelbooruTagsFilter) : GelbooruTagsRequest() {
    override val url = "$internalUrl&json=1${filter.toUrl()}"
}
