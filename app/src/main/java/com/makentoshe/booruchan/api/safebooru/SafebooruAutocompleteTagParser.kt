package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.api.Tag.Companion.defineTagType
import org.jsoup.Jsoup

class SafebooruAutocompleteTagParser : Parser<List<Tag>> {
    override fun parse(data: String): List<Tag> {
        val result = mutableListOf<Tag>()
        val body = Jsoup.parse(data).body()
        val tagstable = body.select(".highlightable")
        val sas = tagstable.select("tr")
        sas.subList(0, 10).forEachIndexed { i, it ->
            //pass table titles
            if (i == 0) return@forEachIndexed

            val data = it.select("td")
            val count = data[0].text()
            val title = data[1].text()
            val type = data[2].text().split(" ")[0]
            val ambiguous = data[2].text().contains("ambiguous")

            val tag = SafebooruTag(title = title, type = defineTagType(type), ambiguous = ambiguous)
            result.add(tag)
        }

        return result
    }

}