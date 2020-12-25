package com.makentoshe.booruchan.repository.decorator

import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.Repository
import com.makentoshe.booruchan.repository.StreamDownloadRepository

class StreamDownloadRepositoryDecoratorFile(
    private val streamDownloadRepository: StreamDownloadRepository
) : Repository<Post, ByteArray> {
    override fun get(key: Post): ByteArray? {
        return streamDownloadRepository.get(key.fileUrl)
    }
}