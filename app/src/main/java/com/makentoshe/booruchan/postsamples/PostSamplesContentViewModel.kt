package com.makentoshe.booruchan.postsamples

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.NotificationInterface
import com.makentoshe.booruchan.UnitRxController
import com.makentoshe.booruchan.postsamples.view.PermissionChecker
import com.makentoshe.viewmodel.ViewModel
import io.reactivex.disposables.CompositeDisposable

class PostSamplesContentViewModel : ViewModel() {
    var position = 0
        private set
//    private lateinit var startDownloadController: UnitRxController
//    private lateinit var permissionChecker: PermissionChecker
//    private lateinit var notificationInterface: NotificationInterface
    private lateinit var booru: Booru
//    private val disposables = CompositeDisposable()

    class Factory(
        private val position: Int,
        private val booru: Booru
//        private val startDownloadController: UnitRxController,
//        private val permissionChecker: PermissionChecker,
//        private val notificationInterface: NotificationInterface
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplesContentViewModel()
            viewModel.position = position
            viewModel.booru = booru

            return viewModel as T
        }
    }
}