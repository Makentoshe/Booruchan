package com.makentoshe.booruchan.screen.posts.viewmodel

import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.api.component.tag.Tag

class TagsViewModel(initialTags: Set<Tag>) : ViewModel() {

    val set: MutableSet<Tag> = HashSet<Tag>().apply { addAll(initialTags) }

}

