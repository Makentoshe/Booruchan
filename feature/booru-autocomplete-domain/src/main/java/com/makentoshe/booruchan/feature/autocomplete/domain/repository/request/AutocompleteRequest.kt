package com.makentoshe.booruchan.feature.autocomplete.domain.repository.request

data class AutocompleteRequest(
    val host: String,
    val query: String,
)