package com.makentoshe.booruchan.screen.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.BooruToolbarUiInflater
import com.makentoshe.booruchan.screen.arguments
import com.makentoshe.booruchan.screen.search.model.TagsController
import com.makentoshe.booruchan.screen.search.model.TagsControllerImpl
import org.jetbrains.anko.*

class PostsFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private val tagsController: TagsController by lazy {
        TagsHolderViewModel.create(this, TagsControllerImpl())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        PostsUiToolbarInflator(booru).inflate(view)
        PostsUiToolbarSearchInflator(childFragmentManager, tagsController).inflate(view)

        parentFragment?.view?.findViewById<DrawerLayout>(R.id.booru_drawer)?.let {
            BooruToolbarUiInflater(it).inflate(view)
        }
    }

    companion object {
        private const val BOORU = "Booru"

        fun create(booru: Booru) = PostsFragment().apply {
            this.booru = booru
        }
    }
}

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
            val factory = TagsHolderViewModel.Factory(tagsController)
            return ViewModelProviders.of(fragment, factory)[TagsHolderViewModel::class.java]
        }
    }
}