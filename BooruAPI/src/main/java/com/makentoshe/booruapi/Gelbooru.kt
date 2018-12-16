package com.makentoshe.booruapi

import com.makentoshe.network.HttpClient
import com.makentoshe.parser.JsonTagParser
import java.io.InputStream


class GelbooruApi: BooruApi {
    override fun getCustomRequest(request: String): String {
        return "https://gelbooru.com$request"
    }

    override fun getAutocompleteRequest(term: String): String {
        return getCustomRequest("/index.php?page=autocomplete&term=$term")
    }
}

class Gelbooru(private val httpClient: HttpClient): Booru(GelbooruApi()){
    override fun customGet(request: String): InputStream {
        return httpClient.get(api.getCustomRequest(request)).stream()
    }

    override fun autocomplete(term: String): List<Tag> {
        return JsonTagParser().parse(httpClient.get(api.getAutocompleteRequest(term)).stream())
    }
}