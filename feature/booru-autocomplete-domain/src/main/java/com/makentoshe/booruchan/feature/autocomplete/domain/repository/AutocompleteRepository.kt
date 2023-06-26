package com.makentoshe.booruchan.feature.autocomplete.domain.repository

import com.makentoshe.booruchan.feature.autocomplete.domain.repository.request.AutocompleteRequest
import com.makentoshe.booruchan.feature.autocomplete.domain.repository.response.AutocompleteResponse
import com.makentoshe.booruchan.feature.context.BooruSystem

interface AutocompleteRepository {

    val supportedBooruSystems: List<BooruSystem>

    suspend fun getSearchTags(request: AutocompleteRequest): AutocompleteResponse

}

