package com.makentoshe.booruimageview

import com.makentoshe.api.NetworkExecutorBuilder
import com.makentoshe.api.cache.CacheBuilder
import com.makentoshe.api.repository.RepositoryBuilder
import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.entitiy.Post
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Performs [Post] downloading.
 *
 * @param repositoryBuilder builds a repository.
 * @param cacheBuilder builds a cache.
 */
class PostsDownload(private val repositoryBuilder: RepositoryBuilder, private val cacheBuilder: CacheBuilder) {

    /** Performs networking */
    private val networkExecutor = NetworkExecutorBuilder.buildSmartGet()

    /** Repository results will be cached */
    private val cache = cacheBuilder.buildPostsCache()

    /** Returns a [Post] instances by [PostsRequest] */
    private val repository = repositoryBuilder.buildPostsRepository(networkExecutor).wrapCache(cache)

    /** Performs downloading from repository */
    fun execute(request: PostsRequest, action: (Post?, Throwable?) -> Unit): Disposable {
        return Single.just(request).observeOn(Schedulers.io()).map(repository::get).map { it.first() }
            .observeOn(AndroidSchedulers.mainThread()).subscribe(action)
    }
}