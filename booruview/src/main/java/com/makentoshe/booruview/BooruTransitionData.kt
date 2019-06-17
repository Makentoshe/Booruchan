package com.makentoshe.booruview

import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import java.io.Serializable

/** Data class contains main data used in API requests */
data class BooruTransitionData(val booru: Booru, val tags: Set<Tag>): Serializable