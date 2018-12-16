package com.makentoshe.booruapi

interface BooruApi {
    fun getCustomRequest(request: String): String
    fun getAutocompleteRequest(term: String): String
}