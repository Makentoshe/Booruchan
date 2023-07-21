package com.makentoshe.booruchan.extension.parser

import com.makentoshe.booruchan.extension.entity.NetworkAutocompleteTag

interface AutoCompleteTagParser {
    fun parse(string: String): List<NetworkAutocompleteTag>
}