package com.makentoshe.booruchan.repository.stream

import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.Repository

class StreamDownloadRepositoryDecoratorFile(
    private val streamDownloadRepository: StreamDownloadRepository
) : Repository<Post, ByteArray> {
    override fun get(key: Post): ByteArray? {
        return streamDownloadRepository.get(key.fileUrl)
    }
}