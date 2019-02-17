package com.makentoshe.booruchan.postpreview

import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.postpreview.model.NavigationController
import com.makentoshe.booruchan.postsamples.PostSamplesScreen
import com.makentoshe.viewmodel.ViewModel
import ru.terrakok.cicerone.Router

class NavigatorViewModel : ViewModel(), NavigationController {

    private lateinit var router: Router
    private var position = 0
    private lateinit var booru: Booru

    override fun onSampleScreenNavigate(itemPosition: Int) {
        router.navigateTo(PostSamplesScreen(itemPosition, position, booru))
    }

    class Factory(
        private val position: Int,
        private val router: Router,
        private val booru: Booru
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = NavigatorViewModel()

            viewModel.router = router
            viewModel.position = position
            viewModel.booru = booru

            return viewModel as T
        }
    }
}