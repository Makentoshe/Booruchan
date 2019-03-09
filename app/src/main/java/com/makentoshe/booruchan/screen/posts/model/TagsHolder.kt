package com.makentoshe.booruchan.screen.posts.model

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Tag

class TagsHolder : ViewModel() {

    val set: MutableSet<Tag> = HashSet()

    companion object {
        fun create(fragment: Fragment, tags: Set<Tag> = setOf()): TagsHolder {
            return ViewModelProviders.of(fragment)[TagsHolder::class.java].apply {
                set.addAll(tags)
            }
        }
    }
}