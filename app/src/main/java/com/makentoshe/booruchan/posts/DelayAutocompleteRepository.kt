package com.makentoshe.booruchan.posts

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.repository.Repository

class DelayAutocompleteRepository(private val booru: Booru) : Repository<CharSequence, List<Tag>> {
    override fun get(key: CharSequence) = booru.autocomplete(key.toString())
}