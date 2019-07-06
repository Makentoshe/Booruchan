package com.makentoshe.boorusamplesview.model


import com.makentoshe.api.NetworkExecutorBuilder
import com.makentoshe.api.SimpleStreamDownloadListener
import com.makentoshe.api.cache.CacheBuilder
import com.makentoshe.api.repository.RepositoryBuilder
import com.makentoshe.boorulibrary.entitiy.Post
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Performs a [ByteArray] downloading.
 *
 * @param repositoryBuilder builds a repository.
 * @param cacheBuilder builds a cache.
 */
class ByteArrayPreviewDownload(
    private val repositoryBuilder: RepositoryBuilder,
    private val cacheBuilder: CacheBuilder
) : Download<Post, ByteArray>() {

    /** Emitter for a progress events */
    private val downloadProgressSubject = PublishSubject.create<Float>()

    /** Observable for a progress events */
    val downloadProgressObservable: Observable<Float>
        get() = downloadProgressSubject

    /** Stream download listener. Used for monitoring a progress events */
    private val downloadListener = SimpleStreamDownloadListener().apply {
        var count = 0f
        onDownloading { bytes, total ->
            count += bytes.size
            downloadProgressSubject.onNext(count / total)
        }
    }

    /** Performs networking operations */
    private val networkExecutor = NetworkExecutorBuilder.buildSmartGet(null, downloadListener)

    /** Repository results will be cached */
    private val cache = cacheBuilder.buildPreviewCache()

    /** Returns a [ByteArray] by [Post] */
    override val repository = repositoryBuilder.buildPreviewRepository(networkExecutor).wrapCache(cache)
}
