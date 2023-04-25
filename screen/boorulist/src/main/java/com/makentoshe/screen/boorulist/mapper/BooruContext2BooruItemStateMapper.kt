package com.makentoshe.screen.boorulist.mapper

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.screen.boorulist.viewmodel.BooruItemHealthState
import com.makentoshe.screen.boorulist.viewmodel.BooruItemState
import javax.inject.Inject

class BooruContext2BooruItemStateMapper @Inject constructor() {
    fun map(
        booruContext: BooruContext,
        health: BooruItemHealthState = BooruItemHealthState.Loading,
    ) = BooruItemState(
        title = booruContext.title,
        url = booruContext.host.url,
        health = health,
    )
}