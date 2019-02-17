package com.makentoshe.booruchan.postsamples

import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.controllers.SimpleRxController
import com.makentoshe.viewmodel.ViewModel
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Router
import java.io.Serializable

class PostSamplesViewModel private constructor() : ViewModel() {

    private var pagePosition: Int = 0
    private var itemPosition: Int = 0
    private lateinit var router: Router
    private lateinit var startDownloadRxController: StartDownloadRxController

    fun onDownloadIconClick(ignored: View) = startDownloadRxController.action(Unit)

//    fun getSamplesVerticalViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
//        return SamplesVerticalViewPagerAdapter(
//            fragmentManager,
//            pagePosition,
//            startDownloadRxController
//        )
//    }

    fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        return true
    }

    /* Returns to the previous screen. Same as onBackButton press. */
    fun exit() = router.exit()

    class Factory(
        private val pagePosition: Int,
        private val itemPosition: Int,
        private val router: Router
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplesViewModel()
            val startDownloadRxController = StartDownloadRxController()

            viewModel.startDownloadRxController = startDownloadRxController
            viewModel.pagePosition = pagePosition
            viewModel.itemPosition = itemPosition
            viewModel.router = router
            return viewModel as T
        }
    }
}

class StartDownloadRxController : SimpleRxController<Unit, Unit>(BehaviorSubject.create()), Serializable {
    override fun action(param: Unit) = observable.onNext(param)
}