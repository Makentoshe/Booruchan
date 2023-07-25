package com.makentoshe.screen.boorulist.mapper

import com.makentoshe.booruchan.extension.base.Source
import com.makentoshe.screen.boorulist.entity.SourceUiState
import javax.inject.Inject

class Source2SourceUiStateMapper @Inject constructor() {
    fun map(source: Source) = SourceUiState(
        title = source.title,
    )
}