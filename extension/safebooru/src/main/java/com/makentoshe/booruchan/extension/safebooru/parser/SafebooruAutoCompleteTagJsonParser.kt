package com.makentoshe.booruchan.extension.safebooru.parser

import com.makentoshe.booruchan.extension.parser.AutoCompleteTagParser
import com.makentoshe.booruchan.extension.safebooru.entity.NetworkSafebooruAutocompleteTag
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SafebooruAutoCompleteTagJsonParser : AutoCompleteTagParser {
    override fun parse(string: String): List<NetworkSafebooruAutocompleteTag> {
        return Json.decodeFromString(string)
    }
}