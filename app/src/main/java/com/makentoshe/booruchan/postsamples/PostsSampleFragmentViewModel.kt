package com.makentoshe.booruchan.postsamples

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.postsamples.model.*
import kotlinx.coroutines.*
import ru.terrakok.cicerone.Router
import java.lang.Exception

class PostsSampleFragmentViewModel(
    private val booru: Booru,
    val startPosition: Int,
    private val postsRepository: PostsRepository,
    private val sampleRepository: ImageRepository,
    private val router: Router,
    fileRepository: ImageRepository,
    private val requestPermissionController: RequestPermissionController
) : FragmentViewModel() {

    private val sampleViewPagerCurrentItemController = SampleViewPagerCurrentItemController(startPosition)
    private val samplePageBlockController = SamplePageBlockController()
    private val fileDownloadController = FileImageDownloadController(this, fileRepository)
    private val confirmFileDownloadController =
        ConfirmFileDownloadController()
    private val fileImageDownloadPerformer =
        FileImageDownloadPerformer(booru, confirmFileDownloadController)

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

    fun onNewPageBlockCommandListener(action: (SamplePageBlockController.Command) -> Unit) =
        samplePageBlockController.subscribe(action)

    fun onFileImageLoadListener(position: Int, action: (DownloadResult<Post>, DownloadResult<ByteArray>) -> Unit) =
        launch {
            val post = getPost(position).await()
            fileDownloadController.subscribe(post) { action(post, it) }
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

    override fun onUiRecreate() {
        super.onUiRecreate()
        sampleViewPagerCurrentItemController.clear()
        samplePageBlockController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        sampleViewPagerCurrentItemController.clear()
        samplePageBlockController.clear()
    }

}

