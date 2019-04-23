package com.makentoshe.booruchan.screen.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.BooruHolderImpl
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.repository.FileImageRepository
import com.makentoshe.booruchan.repository.PostsRepository
import com.makentoshe.booruchan.repository.PreviewImageRepository
import com.makentoshe.booruchan.repository.SampleImageRepository
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import com.makentoshe.booruchan.repository.decorator.CachedRepository
import com.makentoshe.booruchan.screen.posts.controller.TagsHolderImpl
import com.makentoshe.booruchan.screen.posts.model.PostPageGridAdapter
import com.makentoshe.booruchan.screen.posts.model.getItemsCountInRequest
import com.makentoshe.booruchan.screen.posts.view.PostPageUi
import com.makentoshe.booruchan.screen.samples.SampleScreen
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.viewmodel.ext.koin.getViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import java.io.Serializable

class PostsPageFragment : Fragment() {

    init {
        currentScope.get<Fragment>(named(PostsPageModule.FRAGMENT)) { parametersOf(this) }
    }

    private val router: Router by inject()

    private var position: Int
        get() = arguments!!.getInt(POS)
        set(value) = arguments().putInt(POS, value)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private val viewmodel by viewModel<PostsPageViewModel> { parametersOf(booru, tags, position) }

    private val contentController by currentScope.inject<PostsPageContentController>()

    //Repository returns preview image by post
    private val previewsRepository by lazy {
        val cache = ImageInternalCache(requireContext(), InternalCache.Type.PREVIEW)
        val source = PreviewImageRepository(booru)
        CachedRepository(cache, source)
    }

    //Repository returns sample image by post
    //Used when preview image can not be decoded
    private val samplesRepository by lazy {
        val cache = ImageInternalCache(requireContext(), InternalCache.Type.SAMPLE)
        val source = SampleImageRepository(booru)
        CachedRepository(cache, source)
    }

    //Repository returns file image by post
    //Used when preview image and sample image can't be decoded
    private val filesRepository by lazy {
        val cache = ImageInternalCache(requireContext(), InternalCache.Type.FILE)
        val source = FileImageRepository(booru)
        CachedRepository(cache, source)
    }

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewmodel.init()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostPageUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        contentController.bindView(view)
//        val disposable = Single.just(postsRepository)
//            .subscribeOn(Schedulers.io())
//            .map { it.get(Posts.Request(getItemsCountInRequest(requireContext()), tags, position))!! }
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnError { onError(view, it) }
//            .subscribe { posts -> onComplete(view, posts) }
//        disposables.add(disposable)
    }

    private fun onComplete(view: View, posts: List<Post>) {
        if (posts.isEmpty()) {
            onError(view, Exception(getString(R.string.posts_ran_out)))
//            parentFragment?.onActivityResult(RequestCode.postpage, position, Intent())
            return
        }

        val progress = view.find<ProgressBar>(R.id.posts_page_progress)
        val gridview = view.find<GridView>(R.id.posts_page_gridview)

        progress.visibility = View.GONE
        gridview.visibility = View.VISIBLE

        val adapter = PostPageGridAdapter(view.context, posts, previewsRepository, samplesRepository, filesRepository)
        //when subscribed on observable - the disposable will be return for storing and dispose in future
        adapter.setOnSubscribeListener {
            disposables.add(it)
        }
        gridview.adapter = adapter

        //click on grid element starts a new screen - samples,
        //where images in sample resolution will be displayed
        view.find<GridView>(R.id.posts_page_gridview).setOnItemClickListener { _, _, itempos, _ ->
            val position = this.position * getItemsCountInRequest(requireContext()) + itempos
            val screen = SampleScreen(position, booru, tags)
            router.navigateTo(screen)
        }
    }

    private fun onError(view: View, throwable: Throwable) {
        val progress = view.find<ProgressBar>(R.id.posts_page_progress)
        val message = view.find<TextView>(R.id.posts_page_textview)
        val messagetext = StringBuilder(throwable.localizedMessage).append("\n")
        messagetext.append(R.string.tap_for_retry)
        message.text = messagetext
        message.visibility = View.VISIBLE
        progress.visibility = View.GONE

        view.setOnClickListener {
            progress.visibility = View.VISIBLE
            message.text = ""
            message.visibility = View.GONE
            view.setOnClickListener(null)
            onViewCreated(view, null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
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

object PostsPageModule {

    const val FRAGMENT = "PostsPageFragment"

    private fun Scope.getViewModel(): PostsPageViewModel {
        val fragment = get<Fragment>(named(FRAGMENT))
        return getViewModel(fragment)
    }


    val module = module {

        scope(named<PostsPageFragment>()) {
            scoped(named(FRAGMENT)) { (fragment: Fragment) -> fragment }
        }

//        scoped { PostPageGridElementUiFactory() }
//        scoped { PostPageGridElementControllerFactory(get()) }
//        scoped { PostPageGridAdapterFactory(get(), get()) }
        scoped { PostsPageContentController(getViewModel()) }

        viewModel { (booru: Booru, tags: Set<Tag>, position: Int) ->
            val booruHolder = BooruHolderImpl(booru)
            val tagsHolder = TagsHolderImpl(tags)
            val disposables = CompositeDisposable()
            val request = get<Posts.Request> { parametersOf(tags, position) }
            val repository = CachedRepository(PostInternalCache(get()), PostsRepository(booru))
            val postsDownloadController = PostsDownloadControllerImpl(repository, request, disposables)
            PostsPageViewModel(booruHolder, tagsHolder, position, postsDownloadController, disposables)
        }
    }
}

class PostsPageContentController(
    private val postsDownloadEventListener: PostsDownloadEventListener
//    private val adapterFactory: PostPageGridAdapterFactory
) {

    fun bindView(view: View) {
        //change view on posts loading failed
        postsDownloadEventListener.onError {
            bindViewOnError(view, it)
        }
        //change view on posts loading success
        postsDownloadEventListener.onSuccess {
            bindViewOnSuccess(view, it)
        }
    }

    private fun bindViewOnSuccess(view: View, posts: List<Post>) {
        if (posts.isEmpty()) {
            return bindViewOnError(view, Exception(view.context.getString(R.string.posts_ran_out)))
        }

        hideProgressBar(view)
        showGridElements(view, posts)
        setOnGridElementClickListener(view)
    }

    private fun hideProgressBar(view: View) {
        val progress = view.find<ProgressBar>(R.id.posts_page_progress)
        progress.visibility = View.GONE
    }

    private fun showGridElements(view: View, posts: List<Post>) {
        val gridview = view.find<GridView>(R.id.posts_page_gridview)
        gridview.visibility = View.VISIBLE

        println("Create adapter")
//        val adapter = adapterFactory.build(posts)
//        gridview.adapter = adapter
    }

    private fun setOnGridElementClickListener(view: View) {
//        val gridview = view.find<GridView>(R.id.posts_page_gridview)
//        gridview.setOnItemClickListener { _, _, itempos, _ ->
//            val position = this.position * getItemsCountInRequest(requireContext()) + itempos
//            val screen = SampleScreen(position, booru, tags)
//            router.navigateTo(screen)
//        }
    }

    private fun bindViewOnError(view: View, throwable: Throwable) {
        val messagetext = StringBuilder(throwable.localizedMessage).append("\n")
            .append(R.string.tap_for_retry).toString()

        displayMessage(view, messagetext)
        hideProgressBar(view)

//        view.setOnClickListener {
//            progress.visibility = View.VISIBLE
//            message.text = ""
//            message.visibility = View.GONE
//            view.setOnClickListener(null)
//            onViewCreated(view, null)
//        }
    }

    private fun displayMessage(view: View, messagetext: String) {
        val messageview = view.find<TextView>(R.id.posts_page_textview)
        messageview.text = messagetext
        messageview.visibility = View.VISIBLE
    }

    private fun showProgressBar(view: View) {
        val progress = view.find<ProgressBar>(R.id.posts_page_progress)
        progress.visibility = View.VISIBLE
    }
}
//
//class PostPageGridAdapterFactory(
//    private val uiFactory: PostPageGridElementUiFactory,
//    private val controllerFactory: PostPageGridElementControllerFactory
//) {
//    fun build(posts: List<Post>): BaseAdapter {
//        return PostPageGridAdapter(posts, uiFactory, controllerFactory)
//    }
//}

//class PostPageGridAdapter(
//    private val posts: List<Post>,
//    private val uiFactory: PostPageGridElementUiFactory
////    private val controllerFactory: PostPageGridElementControllerFactory
//) : BaseAdapter() {
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        val view = convertView ?: uiFactory.createView(parent!!.context)
////        controllerFactory.createController(getItem(position)).bindView(view)
//        return view
//    }
//
//    override fun getItem(position: Int) = posts[position]
//
//    override fun getItemId(position: Int) = position.toLong()
//
//    override fun getCount() = posts.size
//}
