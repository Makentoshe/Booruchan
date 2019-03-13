package com.makentoshe.booruchan.api

import java.io.Serializable

interface Booru : Serializable {

    fun getAutocomplete(): Autocomplete

    fun getPosts(): Posts

    fun getTagParser(): Parser<List<Tag>>

    fun getPostParser(): Parser<List<Post>>
}