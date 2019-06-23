package com.makentoshe.boorupostview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.api.*
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.model.GridElementControllerHolder
import com.makentoshe.boorupostview.model.GridElementViewModelHolder
import com.makentoshe.boorupostview.model.ItemsCountCalculator
import com.makentoshe.boorupostview.presenter.GridScrollElementPresenter
import com.makentoshe.boorupostview.view.PostsGridScrollElementFragmentUi
import com.makentoshe.boorupostview.viewmodel.GridScrollElementFragmentViewModel
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

/**
 * Fragment for a single view pager element contains a grid view for displaying a posts,
 * progress bar for displaying a progress work and a message view for displaying an error messages.
 */
class GridScrollElementFragment : Fragment() {

    /** Will be initialized at once */
    private var controllerHolder: GridElementControllerHolder? = null

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

    /** Creates presenter and binds a ui to it */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imageRepositoryBuilder = ImageRepositoryBuilder(booru)
        // get a viewmodel instance
        val viewmodel = getViewModel()
        // get a viewmodels holder for grid view adapter
        val viewModelHolderBuilder = GridElementViewModelHolder.Builder(this, imageRepositoryBuilder)
        // create a presenter instance
        val presenter = GridScrollElementPresenter(disposables, viewmodel, viewModelHolderBuilder, countCalc)
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

    /** Creates a [PostsRepository] instance with cache support */
    private fun buildPostRepository(): Repository<PostsRequest, List<Post>> {
        val networkExecutor = NetworkExecutorBuilder.buildSmartGet()
        val repository = PostsRepository(booru, networkExecutor)
        val cache = PostDiskCache.build(requireContext())
        return RepositoryCache(cache, repository)
    }

    /** Returns a viewmodel associated with this fragment or creates a new one */
    private fun getViewModel(): GridScrollElementFragmentViewModel {
        // request for receiving a list of posts
        val request = DefaultPostsRequest(countCalc.getItemsCountTotal(requireContext()), tags, position)
        // repository for requesting a posts
        val repository = buildPostRepository()

        val factory = GridScrollElementFragmentViewModel.Factory(request, repository, controllerHolder)
        return ViewModelProviders.of(this, factory)[GridScrollElementFragmentViewModel::class.java]
    }

    /** Release disposables */
    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        private const val TAGS = "Tags"
        private const val POSITION = "Position"
        private const val BOORU = "Booru"
        fun build(
            booru: Booru, tags: Set<Tag>, position: Int, controllerHolder: GridElementControllerHolder
        ): Fragment {
            val fragment = GridScrollElementFragment()
            fragment.booru = booru
            fragment.tags = tags
            fragment.position = position
            fragment.controllerHolder = controllerHolder
            return fragment
        }
    }
}

