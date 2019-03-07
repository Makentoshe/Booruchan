package com.makentoshe.booruchan.screen.posts.model

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Tag

class TagsHolderViewModel(private val controller: TagsController) : TagsController, ViewModel() {

    override val tags: Set<Tag>
        get() = controller.tags

    override fun addTag(tag: Tag) {
        controller.addTag(tag)
    }

    override fun removeTag(tag: Tag) {
        controller.removeTag(tag)
    }

    class Factory(private val controller: TagsController) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = TagsHolderViewModel(controller)
            return viewModel as T
        }
    }

    companion object {
        fun create(fragment: Fragment, tagsController: TagsController): TagsHolderViewModel {
            val factory = Factory(tagsController)
            return ViewModelProviders.of(
                fragment,
                factory
            )[TagsHolderViewModel::class.java]
        }
    }
}