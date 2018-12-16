package com.makentoshe.booruapi

import com.makentoshe.network.HttpClient
import com.makentoshe.parser.ParserFactory
import com.makentoshe.parser.ParserStyle
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
        return ParserFactory()
            .buildFactory(Tag::class)
            .buildParser(ParserStyle.JSON)
            .parse(httpClient.get(api.getAutocompleteRequest(term)).stream())
    }
}