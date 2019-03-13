package com.makentoshe.booruchan.api

import java.io.Serializable

interface Booru : Serializable {

    val title: String

    fun getCustom(): Custom

    fun getAutocomplete(): Autocomplete

    fun getPosts(): Posts

    fun getTagParser(): Parser<List<Tag>>

    fun getPostParser(): Parser<List<Post>>
}