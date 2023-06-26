package com.makentoshe.booruchan.feature.autocomplete.domain.repository.response

import com.makentoshe.booruchan.feature.autocomplete.domain.entity.NetworkSearchTag
import com.makentoshe.booruchan.feature.autocomplete.domain.repository.request.AutocompleteRequest

data class AutocompleteResponse(
    val request: AutocompleteRequest,
    val searchTags: List<NetworkSearchTag>,
)