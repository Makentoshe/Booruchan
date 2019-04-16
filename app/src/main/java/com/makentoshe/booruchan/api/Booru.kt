package com.makentoshe.booruchan.api

import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.tag.Tag
import java.io.Serializable

interface Booru : Serializable {

    val title: String

    fun getCustom(params: Map<String, String> = mapOf()): Custom

    fun headCustom(params: Map<String, String> = mapOf()): Custom

    fun getAutocomplete(): Autocomplete

    fun getPosts(): Posts

    fun getAutocompleteTagParser(): Parser<List<Tag>>

    fun getPostParser(): Parser<List<Post>>
}
