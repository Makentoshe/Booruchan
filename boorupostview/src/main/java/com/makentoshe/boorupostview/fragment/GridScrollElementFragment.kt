package com.makentoshe.boorupostview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.makentoshe.api.*
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.model.ItemsCountCalculator
import com.makentoshe.boorupostview.presenter.GridScrollElementRxPresenter
import com.makentoshe.boorupostview.view.PostsGridScrollElementFragmentUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

/**
 * Fragment for a single view pager element contains a grid view for displaying a posts,
 * progress bar for displaying a progress work and a message view for displaying an error messages.
 */
class GridScrollElementFragment : Fragment() {

    /** Calculator for the grid view elements count and space */
    private val countCalc = ItemsCountCalculator()

    /** Contains disposables which must be released on destroy lifecycle event */
    private val disposables = CompositeDisposable()

    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    private var position: Int
        set(value) = (arguments ?: Bundle().also { arguments = it }).putInt(POSITION, value)
        get() = arguments!!.getInt(POSITION)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsGridScrollElementFragmentUi(countCalc).createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val previewRepositoryBuilder = PreviewImageRepository.Builder(booru)
        // get request for receiving a list of posts
        val request = DefaultPostsRequest(countCalc.getItemsCountTotal(requireContext()), tags, position)
        // create a presenter instance
        val presenter = GridScrollElementRxPresenter(
            disposables, buildPostRepository(), request, previewRepositoryBuilder
        )
        // bind a grid view
        val gridView = view.findViewById<GridView>(com.makentoshe.boorupostview.R.id.gridview)
        presenter.bindGridView(gridView)
        // bind a progress bar
        val progressbar = view.findViewById<ProgressBar>(com.makentoshe.boorupostview.R.id.progressbar)
        presenter.bindProgressBar(progressbar)
        //bind a message view
        val messageview = view.findViewById<TextView>(com.makentoshe.boorupostview.R.id.messageview)
        presenter.bindMessageView(messageview)
    }

    private fun buildPostRepository(): Repository<PostsRequest, List<Post>> {
        val networkExecutor = NetworkExecutorBuilder.buildSmartGet()
        val repository = PostsRepository(booru, networkExecutor)
        val cache = PostDiskCache(DiskCache(PostDiskCache.getDir(requireContext())))
        return RepositoryCache(cache, repository)
    }

    override fun onDestroy() = super.onDestroy().run { disposables.clear() }

    companion object {
        private const val TAGS = "Tags"
        private const val POSITION = "Position"
        private const val BOORU = "Booru"
        fun build(booru: Booru, tags: Set<Tag>, position: Int): Fragment {
            val fragment = GridScrollElementFragment()
            fragment.booru = booru
            fragment.tags = tags
            fragment.position = position
            return fragment
        }
    }
}