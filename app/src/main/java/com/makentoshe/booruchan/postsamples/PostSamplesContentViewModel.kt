package com.makentoshe.booruchan.postsamples

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.NotificationInterface
import com.makentoshe.booruchan.UnitRxController
import com.makentoshe.booruchan.postsamples.view.PermissionChecker
import com.makentoshe.repository.Repository
import com.makentoshe.viewmodel.ViewModel
import io.reactivex.disposables.CompositeDisposable

class PostSamplesContentViewModel : ViewModel() {
    var position = 0
        private set
    private lateinit var startDownloadController: UnitRxController
    private lateinit var permissionChecker: PermissionChecker
    private lateinit var notificationInterface: NotificationInterface
    private val disposables = CompositeDisposable()

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
        private val position: Int,
        private val startDownloadController: UnitRxController,
        private val permissionChecker: PermissionChecker,
        private val notificationInterface: NotificationInterface
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplesContentViewModel()

            viewModel.permissionChecker = permissionChecker
            viewModel.startDownloadController = startDownloadController
            viewModel.notificationInterface = notificationInterface
            viewModel.position = position

            return viewModel as T
        }
    }
}