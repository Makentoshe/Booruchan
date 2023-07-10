package com.makentoshe.booruchan.screen.boorucontent.mapper

import com.makentoshe.booruchan.screen.boorucontent.domain.AutoCompleteTagUi
import com.makentoshe.booruchan.screen.boorucontent.domain.SearchTagUi
import javax.inject.Inject

class AutoCompleteTagUi2SearchTagUiMapper @Inject constructor() {
    fun map(autoCompleteTagUi: AutoCompleteTagUi) = SearchTagUi(
        title = autoCompleteTagUi.title,
        value = autoCompleteTagUi.value,
    )
}