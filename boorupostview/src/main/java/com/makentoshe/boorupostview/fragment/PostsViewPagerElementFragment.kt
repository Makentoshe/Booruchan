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
import com.makentoshe.api.cache.CacheBuilder
import com.makentoshe.api.repository.Repository
import com.makentoshe.api.repository.RepositoryBuilder
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.model.GridElementControllerHolder
import com.makentoshe.boorupostview.model.ItemsCountCalculator
import com.makentoshe.boorupostview.presenter.PostsViewPagerElementPresenter
import com.makentoshe.boorupostview.view.PostsViewPagerElementFragmentUi
import com.makentoshe.boorupostview.viewmodel.ViewPagerElementFragmentViewModel
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

/**
 * Fragment for a single view pager element contains a grid view for displaying a posts,
 * progress bar for displaying a progress work and a message view for displaying an error messages.
 */
class PostsViewPagerElementFragment : Fragment() {

    /**
     * Will be initialized at once when fragment instance will be created.
     * Other times this instance will be stored in a viewmodel component.
     */
    private var controllerHolder: GridElementControllerHolder? = null

    /** Calculator for the grid view elements count and space */
    private val countCalc = ItemsCountCalculator()

    /** Contains disposables which must be released on destroy lifecycle event */
    private val disposables = CompositeDisposable()

    /** Current [Booru] API instance used for requests */
    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    /** Set of a tags might used in requests */
    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    /** Current [androidx.viewpager.widget.ViewPager] page index starts from 0 */
    private var position: Int
        set(value) = (arguments ?: Bundle().also { arguments = it }).putInt(POSITION, value)
        get() = arguments!!.getInt(POSITION)

    /** Creates a [PostsRepository] instance with cache support */
    private val postsRepository: Repository<PostsRequest, List<Post>>
    get() = RepositoryBuilder(booru).buildPostRepository(NetworkExecutorBuilder.buildSmartGet())
        .wrapCache(CacheBuilder(requireContext()).buildPostsCache())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsViewPagerElementFragmentUi(countCalc).createView(AnkoContext.create(requireContext()))
    }

    /** Creates presenter and binds a ui to it */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // get a viewmodel instance
        val viewmodel = getViewModel()
        // create a presenter instance
        val presenter = PostsViewPagerElementPresenter(disposables, viewmodel, countCalc)
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

    /** Returns a viewmodel associated with this fragment or creates a new one */
    private fun getViewModel(): ViewPagerElementFragmentViewModel {
        // request for receiving a list of posts
        val request = DefaultPostsRequest(countCalc.getItemsCountTotal(requireContext()), tags, position)

        val factory = ViewPagerElementFragmentViewModel.Factory(request, postsRepository, controllerHolder)
        return ViewModelProviders.of(this, factory)[ViewPagerElementFragmentViewModel::class.java]
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
            val fragment = PostsViewPagerElementFragment()
            fragment.booru = booru
            fragment.tags = tags
            fragment.position = position
            fragment.controllerHolder = controllerHolder
            return fragment
        }
    }
}

