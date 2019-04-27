package com.makentoshe.booruchan.screen.posts.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.posts.page.controller.PostsPageContentController
import com.makentoshe.booruchan.screen.posts.page.view.PostPageUi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.AnkoContext
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.io.Serializable

class PostsPageFragment : Fragment() {

    init {
        //provide current fragment instance to the scope
        currentScope.get<PostsPageFragment> { parametersOf(this) }
    }

    private var position: Int
        get() = arguments!!.getInt(POS)
        set(value) = arguments().putInt(POS, value)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private val viewmodel by viewModel<PostsPageViewModel> {
        parametersOf(booru, tags, position, disposable as CompositeDisposable)
    }

    private val contentController by currentScope.inject<PostsPageContentController>()

    /* Disposable that must be disposed on the onDestroy method */
    private val disposable: Disposable by currentScope.inject<CompositeDisposable>(named(PostsPageModule.DISPOSABLE))

    override fun onCreate(savedInstanceState: Bundle?) {
        viewmodel.init()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostPageUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        contentController.bindView(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
    }

    companion object {
        private const val POS = "Position"
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"

        fun create(position: Int, booru: Booru, tags: Set<Tag>) = PostsPageFragment().apply {
            this.position = position
            this.booru = booru
            this.tags = tags
        }
    }
}

