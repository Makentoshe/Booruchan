package com.makentoshe.boorupostview.model

import android.os.Bundle
import com.makentoshe.boorulibrary.entitiy.Tag
import java.io.Serializable

class PostsViewPagerFragmentStateExtractor {

    /** Extracts a set of the [Tag] from the [Bundle] or return an empty set */
    fun extractTagsFromState(state: Bundle): Set<Tag> {
        val tags = Set::class.java.simpleName
        return if (state.containsKey(tags)) state.get(tags) as Set<Tag> else emptySet()
    }

    /** Package a set of the [Tag] and page number to a [Bundle] */
    fun packageState(tags: Set<Tag>) = Bundle().apply {
        putSerializable(Set::class.java.simpleName, tags as Serializable)
    }
}