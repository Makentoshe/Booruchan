package com.makentoshe.booruchan.feature.autocomplete.mapper

import com.makentoshe.booruchan.extension.entity.NetworkAutocompleteTag
import com.makentoshe.booruchan.feature.entity.AutoCompleteTag
import javax.inject.Inject

class NetworkAutoCompleteTag2AutoCompleteTagMapper @Inject constructor() {
    fun map(networkAutocompleteTag: NetworkAutocompleteTag) = AutoCompleteTag(
        title = networkAutocompleteTag.title,
        value = networkAutocompleteTag.value,
        count = networkAutocompleteTag.count,
    )
}