package com.makentoshe.booruchan.postpreview

import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.postpreview.model.NavigationController
import com.makentoshe.repository.Repository
import com.makentoshe.viewmodel.ViewModel
import ru.terrakok.cicerone.Router

class NavigatorViewModel : ViewModel(), NavigationController {

    private lateinit var router: Router
    private lateinit var postsRepository: Repository<Booru.PostRequest, Posts>
    private lateinit var samplesRepository: Repository<String, ByteArray>
    private var postsCountInRequest = 0
    private var position = 0

    override fun onSampleScreenNavigate(itemPosition: Int) {
        val position = itemPosition + position * postsCountInRequest
//        router.navigateTo(PostSamplesScreen(position, postsRepository, sampleImageRepository))
    }

    class Factory(
        private val postsRepository: Repository<Booru.PostRequest, Posts>,
        private val postsCountInRequest: Int,
        private val position: Int,
        private val router: Router,
        private val samplesRepository: Repository<String, ByteArray>
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = NavigatorViewModel()

            viewModel.router = router
            viewModel.position = position
            viewModel.postsRepository = postsRepository
            viewModel.postsCountInRequest = postsCountInRequest
            viewModel.samplesRepository = samplesRepository

            return viewModel as T
        }
    }
}