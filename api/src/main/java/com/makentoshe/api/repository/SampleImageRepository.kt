package com.makentoshe.api.repository

import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.network.executor.NetworkExecutor

class SampleImageRepository(booru: Booru, networkExecutor: NetworkExecutor) : Repository<Post, ByteArray> {

    private val sample = booru.getSample(networkExecutor)

    override fun get(key: Post) = sample.request(key)
}