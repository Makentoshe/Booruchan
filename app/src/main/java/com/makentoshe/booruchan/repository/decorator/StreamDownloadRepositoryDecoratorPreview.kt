package com.makentoshe.booruchan.repository.decorator

import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.repository.Repository
import com.makentoshe.booruchan.repository.StreamDownloadRepository

class StreamDownloadRepositoryDecoratorPreview(private val streamDownloadRepository: StreamDownloadRepository) :
    Repository<Post, ByteArray> {
    override fun get(key: Post): ByteArray? {
        return streamDownloadRepository.get(key.previewUrl)
    }
}