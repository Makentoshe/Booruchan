package com.makentoshe.booruchan.repository

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag


class DelayAutocompleteRepository(private val booru: Booru) : Repository<CharSequence, List<Tag>> {
    override fun get(key: CharSequence) = booru.getAutocomplete().request(key.toString())
}