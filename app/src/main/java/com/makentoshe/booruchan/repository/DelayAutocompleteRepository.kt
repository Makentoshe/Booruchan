package com.makentoshe.booruchan.repository

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag

class DelayAutocompleteRepository(private val booru: Booru) : Repository<CharSequence, List<Tag>> {
    override fun get(key: CharSequence) = booru.autocomplete(key.toString())
}