package com.makentoshe.booruchan.feature.autocomplete

import com.makentoshe.booruchan.extension.factory.AutoCompleteTagFactory
import com.makentoshe.booruchan.feature.NetworkRepository
import com.makentoshe.booruchan.feature.autocomplete.mapper.NetworkAutoCompleteTag2AutoCompleteTagMapper
import com.makentoshe.booruchan.feature.entity.AutoCompleteTag
import javax.inject.Inject

class FetchAutoCompleteTagsUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val mapper: NetworkAutoCompleteTag2AutoCompleteTagMapper,
) {
    suspend operator fun invoke(
        request: AutoCompleteTagFactory.FetchTagsRequest,
        factory: AutoCompleteTagFactory,
    ): List<AutoCompleteTag> {
        return factory.parseResponse(networkRepository.execute(factory.buildRequest(request))).map(mapper::map)
    }
}