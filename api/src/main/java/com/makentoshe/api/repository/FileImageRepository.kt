package com.makentoshe.api.repository

import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.network.executor.NetworkExecutor

class FileImageRepository(booru: Booru, networkExecutor: NetworkExecutor) : Repository<Post, ByteArray> {

    private val file = booru.getFile(networkExecutor)

    override fun get(key: Post) = file.request(key)
}