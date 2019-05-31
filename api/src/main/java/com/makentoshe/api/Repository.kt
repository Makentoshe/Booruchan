package com.makentoshe.api

import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.booru.gelbooru.Gelbooru
import com.makentoshe.boorulibrary.booru.safebooru.Safebooru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorulibrary.network.executor.NetworkExecutor

interface Repository<K, V> {
    fun get(key: K): V?
}

class BooruRepository: Repository<Any, List<Booru>> {
    override fun get(key: Any): List<Booru> {
        return listOf(Gelbooru(), Safebooru())
    }
}

class AutocompleteRepository(booru: Booru, networkExecutor: NetworkExecutor) : Repository<String, List<Tag>> {

    private val autocomplete = booru.getAutocomplete(networkExecutor)

    override fun get(key: String) = autocomplete.request(key)
}