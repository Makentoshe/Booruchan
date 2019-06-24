package com.makentoshe.api.repository

import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorulibrary.network.executor.NetworkExecutor

class AutocompleteRepository(booru: Booru, networkExecutor: NetworkExecutor) : Repository<String, List<Tag>> {

    private val autocomplete = booru.getAutocomplete(networkExecutor)

    override fun get(key: String) = autocomplete.request(key)
}