package com.makentoshe.boorupostview.model

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.api.CacheBuilder
import com.makentoshe.api.ImageRepositoryBuilder
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorupostview.viewmodel.GridElementViewModel
import org.jetbrains.anko.collections.forEachWithIndex

/**
 * Stores a viewmodels for a [GridScrollElementAdapter]
 */
data class GridElementViewModelHolder(val viewmodels: List<GridElementViewModel>) {

    /**
     * [GridElementViewModelHolder] builder
     */
    class Builder(private val fragment: Fragment, private val imageRepositoryBuilder: ImageRepositoryBuilder) {

        /** Builds a list of a viewmodels. Each model associated to own post by index */
        fun build(posts: List<Post>): GridElementViewModelHolder {
            val viewmodels = ArrayList<GridElementViewModel>()
            val cache = CacheBuilder(fragment.requireContext())
            val imageDecoder = AndroidImageDecoder()

            posts.forEachWithIndex { i, post ->
                val factory = GridElementViewModel.Factory(post, cache, imageRepositoryBuilder, imageDecoder)
                ViewModelProviders.of(fragment, factory)
                    .get(i.toString(), GridElementViewModel::class.java).let(viewmodels::add)
            }
            return GridElementViewModelHolder(viewmodels)
        }
    }
}