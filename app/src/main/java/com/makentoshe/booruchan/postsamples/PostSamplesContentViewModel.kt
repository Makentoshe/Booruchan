package com.makentoshe.booruchan.postsamples

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.NotificationInterface
import com.makentoshe.booruchan.postsamples.model.SamplesContentViewPagerAdapter
import com.makentoshe.booruchan.postsamples.view.FileDownloadService
import com.makentoshe.booruchan.postsamples.view.PermissionChecker
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.Repository
import com.makentoshe.repository.SampleImageRepository
import com.makentoshe.viewmodel.ViewModel
import io.reactivex.disposables.CompositeDisposable

class PostSamplesContentViewModel : ViewModel() {
    var position = 0
        private set

    private var pagePosition: Int = 0
    private var itemPosition: Int = 0
    private lateinit var postsRepository: Repository<Booru.PostRequest, Posts>
    private lateinit var samplesRepository: Repository<Post, ByteArray>
    private lateinit var startDownloadController: StartDownloadRxController
    private lateinit var filesRepository: Repository<String, ByteArray>
    private lateinit var permissionChecker: PermissionChecker
    private lateinit var notificationInterface: NotificationInterface
    private val disposables = CompositeDisposable()

//    fun getViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
//        return SamplesContentViewPagerAdapter(fragmentManager, postsRepository, samplesRepository)
//    }

    fun onStartDownloadControllerListener(action: () -> Unit) {
        disposables.add(startDownloadController.subscribe { action() })
    }

    fun startFileDownload(position: Int, context: Context) {
//        FileDownloadService.startService(context, position, postsRepository, filesRepository, permissionChecker, notificationInterface)
    }

    override fun onCreateView(owner: Fragment) {
        disposables.clear()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    class Factory(
        private val pagePosition: Int,
        private val itemPosition: Int,
        private val postsRepository: Repository<Booru.PostRequest, Posts>,
        private val samplesRepository: Repository<Post, ByteArray>,
        private val startDownloadController: StartDownloadRxController,
        private val permissionChecker: PermissionChecker,
        private val notificationInterface: NotificationInterface
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplesContentViewModel()

            viewModel.position = pagePosition
            viewModel.postsRepository = postsRepository
            viewModel.samplesRepository = samplesRepository
            viewModel.permissionChecker = permissionChecker
            viewModel.startDownloadController = startDownloadController
            viewModel.notificationInterface = notificationInterface

            return viewModel as T
        }
    }
}