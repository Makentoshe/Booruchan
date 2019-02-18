package com.makentoshe.booruchan.postpreview

import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.postpreview.model.NavigationController
import com.makentoshe.booruchan.postsamples.PostSamplesScreen
import com.makentoshe.viewmodel.ViewModel
import ru.terrakok.cicerone.Router

class NavigatorViewModel(private val position: Int) : ViewModel(), NavigationController {

    private lateinit var router: Router
    private lateinit var booru: Booru
    private lateinit var tags: Set<Tag>

    override fun onSampleScreenNavigate(itemPosition: Int) {
        router.navigateTo(PostSamplesScreen(booru, tags, itemPosition + position * 12))
    }

    class Factory(
        private val router: Router,
        private val booru: Booru,
        private val tags: Set<Tag>,
        private val position: Int
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = NavigatorViewModel(position)

            viewModel.router = router
            viewModel.booru = booru
            viewModel.tags = tags
            return viewModel as T
        }
    }
}