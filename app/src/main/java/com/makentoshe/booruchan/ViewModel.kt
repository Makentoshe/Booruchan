package com.makentoshe.booruchan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.booru.BooruFragmentViewModel
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.postpage.PostPageFragmentViewModel
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import com.makentoshe.booruchan.postsamples.PostsSampleFragmentViewModel
import com.makentoshe.booruchan.postsamples.model.SamplePageController
import com.makentoshe.booruchan.postsamplespage.PostSamplePageViewModel
import com.makentoshe.booruchan.postsamplespageimage.PostSamplePagePreviewFragmentViewModel
import com.makentoshe.booruchan.postsamplespageinfo.PostSamplePageInfoFragmentViewModel
import com.makentoshe.booruchan.start.StartFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class CoroutineViewModel : ViewModel(), CoroutineScope {
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}

abstract class FragmentViewModel : CoroutineViewModel() {
    open fun onUiRecreate() = Unit
}

abstract class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = build()
        if (modelClass == viewModel::class.java) return viewModel as T
        return super.create(modelClass)
    }

    abstract fun  build(): ViewModel
}

class StartFragmentViewModelFactory : ViewModelFactory() {
    override fun build() = StartFragmentViewModel()
}

class BooruFragmentViewModelFactory(
    private val booru: Booru,
    private val tags: Set<Tag>
) : ViewModelFactory() {
    override fun build() = BooruFragmentViewModel(booru, tags)
}

class PostFragmentViewModelFactory(
    private val booru: Booru,
    private val drawerController: DrawerController,
    private val tags: Set<Tag>
) : ViewModelFactory() {
    override fun build() = PostsFragmentViewModel(booru, drawerController, tags)
}

class PostPageFragmentViewModelFactory(
    private val booru: Booru,
    private val position: Int,
    private val postsRepository: PostsRepository,
    private val previewsRepository: PreviewImageRepository
) : ViewModelFactory() {
    override fun build() = PostPageFragmentViewModel(booru, position, postsRepository, previewsRepository)
}

class PostSampleFragmentViewModelFactory(
    private val booru: Booru,
    private val position: Int,
    private val postsRepository: PostsRepository,
    private val sampleRepository: ImageRepository
) : ViewModelFactory() {
    override fun build() = PostsSampleFragmentViewModel(booru, position, postsRepository, sampleRepository)
}

class PostSamplePageFragmentViewModelFactory(
    private val position: Int,
    private val samplePageController: SamplePageController,
    private val sampleRepository: ImageRepository,
    private val postsRepository: PostsRepository
) : ViewModelFactory() {
    override fun build() = PostSamplePageViewModel(position, samplePageController, sampleRepository, postsRepository)
}

class PostSamplePageImageFragmentViewModelFactory(
    private val samplesRepository: ImageRepository,
    private val position: Int,
    private val postsRepository: PostsRepository
) : ViewModelFactory() {
    override fun build() = PostSamplePagePreviewFragmentViewModel(samplesRepository, position, postsRepository)
}

class PostSamplePageInfoFragmentViewModelFactory(
    private val position: Int,
    private val postsRepository: PostsRepository
) : ViewModelFactory() {
    override fun build() = PostSamplePageInfoFragmentViewModel(position, postsRepository)
}