package tag.network

import network.GelbooruRequest

sealed class GelbooruTagsRequest: GelbooruRequest() {

    protected val internalUrl = "$host/index.php?page=dapi&s=tag&q=index"

    abstract val url: String

}

data class XmlGelbooruTagsRequest(private val filter: GelbooruTagsFilter) : GelbooruTagsRequest() {
    override val url = "$internalUrl${filter.toUrl()}"
}

data class JsonGelbooruTagsRequest(private val filter: GelbooruTagsFilter) : GelbooruTagsRequest() {
    override val url = "$internalUrl&json=1${filter.toUrl()}"
}
