package com.makentoshe.booruchan.api

import com.makentoshe.booruchan.api.component.tag.Tag
import java.io.Serializable

interface Autocomplete: Serializable {
    fun request(term: String): List<Tag>
}