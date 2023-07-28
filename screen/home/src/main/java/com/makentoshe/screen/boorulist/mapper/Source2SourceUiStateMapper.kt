package com.makentoshe.screen.boorulist.mapper

import com.makentoshe.booruchan.extension.base.Source
import com.makentoshe.screen.boorulist.entity.SourceHealthUi
import com.makentoshe.screen.boorulist.entity.SourceUiState
import javax.inject.Inject

class Source2SourceUiStateMapper @Inject constructor() {
    fun map(source: Source, availability: SourceHealthUi = SourceHealthUi.Loading) = SourceUiState(
        id = source.id,
        host = source.getHost(),
        title = source.title,
        health = availability,
    )

    private fun Source.getHost(): String = try {
        host
    } catch (exception: AbstractMethodError) {
        ""
    }
}