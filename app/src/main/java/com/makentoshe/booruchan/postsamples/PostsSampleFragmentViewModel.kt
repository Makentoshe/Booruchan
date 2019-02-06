package com.makentoshe.booruchan.postsamples

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.postsamples.model.*
import com.makentoshe.repository.FileImageRepository
import com.makentoshe.repository.ImageRepository
import com.makentoshe.repository.PostsRepository
import kotlinx.coroutines.*
import ru.terrakok.cicerone.Router

class PostsSampleFragmentViewModel private constructor() : com.makentoshe.viewmodel.ViewModel() {
    private lateinit var booru: Booru
    private lateinit var postsRepository: PostsRepository
    private lateinit var sampleRepository: ImageRepository
    private lateinit var router: Router
    private lateinit var requestPermissionController: RequestPermissionController
    private lateinit var sampleViewPagerCurrentItemController: SampleViewPagerCurrentItemController
    private lateinit var samplePageBlockController: SamplePageHorizontalScrollBlockController
    private lateinit var fileImageDownloadController: FileImageDownloadController
    private lateinit var confirmFileDownloadController: ConfirmFileDownloadController
    private lateinit var fileImageDownloadPerformer: FileImageDownloadPerformer

    /**
     * @param position post index start from 0.
     */
    fun getPost(position: Int): Deferred<DownloadResult<Post>> = async {
        try {
            val post: Post = withContext(Dispatchers.Default) {
                postsRepository.get(position / postsRepository.count)[position % postsRepository.count]
            }
            return@async DownloadResult(post)
        } catch (e: Exception) {
            return@async DownloadResult<Post>(exception = e)
        }
    }

    /**
     * Starts new search with [tags].
     */
    fun startNewSearch(tags: Set<Tag> = setOf()) = router.newChain(BooruScreen(booru, tags.toHashSet()))

    fun getPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplePagePagerAdapter(fragmentManager, samplePageBlockController, sampleRepository, postsRepository)
    }

    fun onNewPageBlockCommandListener(action: (SamplePageHorizontalScrollBlockController.Command) -> Unit) =
        samplePageBlockController.subscribe(action)

    fun onFileImageLoadListener(position: Int, action: (DownloadResult<Post>, DownloadResult<ByteArray>) -> Unit) =
        launch {
            val post = getPost(position).await()
            fileImageDownloadController.subscribe(post) { action(post, it) }
        }

    fun newPageSelected(page: Int) = sampleViewPagerCurrentItemController.action(page)

    fun onNewPageSelected(action: (Int) -> Unit) = sampleViewPagerCurrentItemController.subscribe(action)

    var selectedPage
        get() = sampleViewPagerCurrentItemController.getPage()
        set(value) = sampleViewPagerCurrentItemController.action(value)

    fun backToPreviews() = router.backTo(BooruScreen(booru))

    fun saveByteArrayAsImageFile(post: Post, byteArray: ByteArray, context: Context) {
        fileImageDownloadPerformer.perform(post, byteArray, context)
    }

    fun onFileDownloadListener(action: (String) -> Unit) = confirmFileDownloadController.subscribe(action)

    fun requestPermission(permission: String) = requestPermissionController.action(permission)

    override fun onCreateView(owner: Fragment) {
        sampleViewPagerCurrentItemController.clear()
        confirmFileDownloadController.clear()
        samplePageBlockController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        sampleViewPagerCurrentItemController.clear()
        confirmFileDownloadController.clear()
        samplePageBlockController.clear()
    }

    class Factory(
        private val booru: Booru,
        private val startPosition: Int,
        private val postsRepository: PostsRepository,
        private val sampleRepository: ImageRepository,
        private val requestPermissionController: RequestPermissionController
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostsSampleFragmentViewModel()
            val fileRepository = FileImageRepository(booru)
            val confirmFileDownloadController = ConfirmFileDownloadController()

            viewModel.booru = booru
            viewModel.postsRepository = postsRepository
            viewModel.sampleRepository = sampleRepository
            viewModel.requestPermissionController = requestPermissionController
            viewModel.confirmFileDownloadController = confirmFileDownloadController
            viewModel.samplePageBlockController = SamplePageHorizontalScrollBlockController()
            viewModel.sampleViewPagerCurrentItemController = SampleViewPagerCurrentItemController(startPosition)
            viewModel.fileImageDownloadController = FileImageDownloadController(viewModel, fileRepository)
            viewModel.fileImageDownloadPerformer = FileImageDownloadPerformer(booru, confirmFileDownloadController)

            return viewModel as T
        }
    }
}

