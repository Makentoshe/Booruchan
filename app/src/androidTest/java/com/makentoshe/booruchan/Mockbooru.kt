package com.makentoshe.booruchan

import com.makentoshe.booruchan.api.*

class Mockbooru : Booru {
    override val title: String
        get() = "Mockbooru"

    override fun getCustom(): Custom {
        TODO("not implemented")
    }

    override fun getAutocomplete(): Autocomplete {
        TODO("not implemented")
    }

    override fun getPosts(): Posts {
        TODO("not implemented")
    }

    override fun getTagParser(): Parser<List<Tag>> {
        TODO("not implemented")
    }

    override fun getPostParser(): Parser<List<Post>> {
        TODO("not implemented")
    }
}