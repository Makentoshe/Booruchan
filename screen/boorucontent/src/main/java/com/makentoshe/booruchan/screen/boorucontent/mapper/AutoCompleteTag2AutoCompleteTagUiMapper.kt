package com.makentoshe.booruchan.screen.boorucontent.mapper

import com.makentoshe.booruchan.feature.entity.AutoCompleteTag
import com.makentoshe.booruchan.screen.boorucontent.domain.AutoCompleteTagUi
import javax.inject.Inject

class AutoCompleteTag2AutoCompleteTagUiMapper @Inject constructor() {
    fun map(autoCompleteTag: AutoCompleteTag) = AutoCompleteTagUi(
        title = autoCompleteTag.title,
        value = autoCompleteTag.value,
        count = autoCompleteTag.count,
    )
}