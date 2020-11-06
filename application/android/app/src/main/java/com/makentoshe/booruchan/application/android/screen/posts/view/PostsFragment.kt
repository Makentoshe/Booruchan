package com.makentoshe.booruchan.application.android.screen.posts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.screen.posts.viewmodel.PostsFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_posts.*
import toothpick.ktp.delegate.inject

class PostsFragment : Fragment() {

    companion object {
        fun build(booruContextTitle: String): PostsFragment {
            val fragment = PostsFragment()
            fragment.arguments.booruContextTitle = booruContextTitle
            return fragment
        }
    }

    private val viewModel by inject<PostsFragmentViewModel>()
    private val disposables by inject<CompositeDisposable>()
    val arguments = Arguments(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val spannedGridLayoutManager = SpannedGridLayoutManager(SpannedGridLayoutManager.Orientation.VERTICAL, 3)
        spannedGridLayoutManager.spanSizeLookup = SpannedGridLayoutManagerLookup(viewModel.postsAdapter)
        fragment_posts_recycler.layoutManager = spannedGridLayoutManager
        fragment_posts_recycler.addItemDecoration(SpacesItemDecoration(8f))
        fragment_posts_recycler.adapter = viewModel.postsAdapter

        viewModel.initialSignal.observeOn(AndroidSchedulers.mainThread()).subscribe {
            if (it.isSuccess) {
                fragment_posts_recycler.visibility = View.VISIBLE
                fragment_posts_progress.visibility = View.GONE
            } else {
                // TODO add displaying initial error
                println(it)
            }
        }.let(disposables::add)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    class Arguments(private val postsFragment: PostsFragment) {

        init {
            val fragment = postsFragment as Fragment
            if (fragment.arguments == null) {
                fragment.arguments = Bundle()
            }
        }

        private val fragmentArguments: Bundle
            get() = postsFragment.requireArguments()

        var booruContextTitle: String
            get() = fragmentArguments.getString(TITLE)!!
            set(value) = fragmentArguments.putString(TITLE, value)

        companion object {
            private const val TITLE = "BooruContext#title"
        }
    }

}