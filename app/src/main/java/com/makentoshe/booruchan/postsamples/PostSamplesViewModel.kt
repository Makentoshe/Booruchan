package com.makentoshe.booruchan.postsamples

import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.postsamples.model.SamplesVerticalViewPagerAdapter
import com.makentoshe.controllers.SimpleRxController
import com.makentoshe.viewmodel.ViewModel
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Router
import java.io.Serializable

class PostSamplesViewModel private constructor() : ViewModel() {

    private lateinit var booru: Booru
    private lateinit var router: Router
    private lateinit var startDownloadRxController: StartDownloadRxController

    fun onDownloadIconClick(ignored: View) = startDownloadRxController.action(Unit)

    fun getSamplesVerticalViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplesVerticalViewPagerAdapter(fragmentManager, startDownloadRxController, booru)
    }

    fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        return true
    }

    /* Returns to the previous screen. Same as onBackButton press. */
    fun exit() = router.exit()

    class Factory(
        private val router: Router,
        private val booru: Booru
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplesViewModel()
            val startDownloadRxController = StartDownloadRxController()

            viewModel.startDownloadRxController = startDownloadRxController
            viewModel.router = router
            viewModel.booru = booru

            return viewModel as T
        }
    }
}

class StartDownloadRxController : SimpleRxController<Unit, Unit>(BehaviorSubject.create()), Serializable {
    override fun action(param: Unit) = observable.onNext(param)
}