package com.makentoshe.booruchan.postsamples

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
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
    private lateinit var postsRepository: PostsRepository
    private lateinit var samplesRepository: SampleImageRepository
    private val disposables = CompositeDisposable()
    private lateinit var startDownloadController: StartDownloadRxController
    private lateinit var filesRepository: Repository<String, ByteArray>
    private lateinit var permissionChecker: PermissionChecker

    fun getViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplesContentViewPagerAdapter(fragmentManager, postsRepository, samplesRepository)
    }

    fun onStartDownloadControllerListener(action: () -> Unit) {
        disposables.add(startDownloadController.subscribe { action() })
    }

    fun startFileDownload(position: Int, context: Context) {
        FileDownloadService.startService(context, position, postsRepository, filesRepository, permissionChecker)
    }

    override fun onCreateView(owner: Fragment) {
        disposables.clear()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    class Factory(
        private val position: Int,
        private val postsRepository: PostsRepository,
        private val samplesRepository: SampleImageRepository,
        private val startDownloadController: StartDownloadRxController,
        private val permissionChecker: PermissionChecker,
        private val filesRepository: Repository<String, ByteArray> = samplesRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplesContentViewModel()

            viewModel.position = position
            viewModel.filesRepository = filesRepository
            viewModel.postsRepository = postsRepository
            viewModel.samplesRepository = samplesRepository
            viewModel.permissionChecker = permissionChecker
            viewModel.startDownloadController = startDownloadController

            return viewModel as T
        }
    }
}