package com.makentoshe.boorupostview.model

import androidx.collection.LongSparseArray
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.api.CacheBuilder
import com.makentoshe.api.RepositoryBuilder
import com.makentoshe.boorulibrary.entitiy.Post
import io.reactivex.disposables.CompositeDisposable

/**
 * Viewmodel component stores all [GridElementController] objects used in current time.
 *
 * @param repositoryBuilder used for creates an any type of repositories for receiving an image data.
 * @param cacheBuilder used for caching results from the repository.
 * @param imageDecoder used for decoding and images from byte array to android [android.graphics.Bitmap].
 */
class GridElementControllerHolderImpl(
    private val repositoryBuilder: RepositoryBuilder,
    private val cacheBuilder: CacheBuilder,
    private val imageDecoder: ImageDecoder
) : ViewModel(), GridElementControllerHolder {

    /** Local storage */
    private val map = LongSparseArray<GridElementController>()

    /** Returns a [GridElementController] instance from local storage. If does not exists - create */
    override fun get(key: Pair<Post, CompositeDisposable>): GridElementController {
        val value = map[key.first.id]
        if (value != null) return value
        val controller = GridElementController(key.first, repositoryBuilder, cacheBuilder, imageDecoder, key.second)
        map.put(key.first.id, controller)
        return get(key)
    }

    /** Removes from local storage */
    override fun remove(key: Post) = map.remove(key.id)

    /**
     * A factory class for creating an Viewmodel component instance used in android architecture components.
     *
     * @param repositoryBuilder used for creates an any type of repositories for receiving an image data.
     * Passed to [GridElementControllerHolderImpl] instance as is.
     *
     * @param cacheBuilder used for caching results from the repository.
     * Passed to [GridElementControllerHolderImpl] instance as is.
     *
     * @param imageDecoder used for decoding and images from byte array to android [android.graphics.Bitmap].
     * Passed to [GridElementControllerHolderImpl] instance as is.
     */
    class Factory(
        private val repositoryBuilder: RepositoryBuilder,
        private val cacheBuilder: CacheBuilder,
        private val imageDecoder: ImageDecoder
    ) : ViewModelProvider.NewInstanceFactory() {

        /** Creates a [GridElementControllerHolderImpl] instance */
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GridElementControllerHolderImpl(repositoryBuilder, cacheBuilder, imageDecoder) as T
        }
    }

}