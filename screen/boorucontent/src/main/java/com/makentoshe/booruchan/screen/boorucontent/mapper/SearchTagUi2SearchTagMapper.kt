package com.makentoshe.booruchan.screen.boorucontent.mapper

import com.makentoshe.booruchan.feature.search.SearchTag
import com.makentoshe.booruchan.screen.boorucontent.domain.SearchTagUi
import javax.inject.Inject

class SearchTagUi2SearchTagMapper @Inject constructor() {
    fun map(searchTagUi: SearchTagUi) = SearchTag(string = searchTagUi.value)
}