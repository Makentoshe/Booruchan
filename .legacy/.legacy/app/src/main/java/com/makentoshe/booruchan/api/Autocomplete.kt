package com.makentoshe.booruchan.api

import java.io.Serializable

interface Autocomplete: Serializable {
    fun request(term: String): List<Tag>
}