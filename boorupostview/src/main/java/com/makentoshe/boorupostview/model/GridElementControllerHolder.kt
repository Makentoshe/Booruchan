package com.makentoshe.boorupostview.model

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.api.CacheBuilder
import com.makentoshe.api.ImageRepositoryBuilder
import com.makentoshe.api.Repository
import com.makentoshe.boorulibrary.entitiy.Post
import io.reactivex.disposables.CompositeDisposable

/**
 * Interface for a container for [GridElementController] objects.
 */
interface GridElementControllerHolder : Repository<Pair<Post, CompositeDisposable>, GridElementController> {

    /** Removes [GridElementController] instance associated by key */
    fun remove(key: Post)

    /**
     * Creates an instance of a [GridElementControllerHolder] class.
     *
     * @param repositoryBuilder used for creates an any type of repositories for receiving an image data.
     * @param cacheBuilder used for caching results from the repository.
     * @param imageDecoder used for decoding and images from byte array to android [android.graphics.Bitmap].
     */
    class Builder(
        private val repositoryBuilder: ImageRepositoryBuilder,
        private val cacheBuilder: CacheBuilder,
        private val imageDecoder: ImageDecoder
    ) {
        /**
         * Build a [GridElementControllerHolder] object.
         * It will be stores in [fragment] while it will not being fully destroyed.
         */
        fun build(fragment: Fragment): GridElementControllerHolder {
            val factory = GridElementControllerHolderImpl.Factory(repositoryBuilder, cacheBuilder, imageDecoder)
            return ViewModelProviders.of(fragment, factory)[GridElementControllerHolderImpl::class.java]
        }
    }

}