package com.makentoshe.api.repository

import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.network.executor.NetworkExecutor

class PreviewImageRepository(booru: Booru, networkExecutor: NetworkExecutor) : Repository<Post, ByteArray> {

    private val preview = booru.getPreview(networkExecutor)

    override fun get(key: Post) = preview.request(key)
}