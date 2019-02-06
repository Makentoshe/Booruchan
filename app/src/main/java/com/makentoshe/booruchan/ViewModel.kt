package com.makentoshe.booruchan

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.booru.BooruFragmentViewModel
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.postpreviews.PostsFragmentViewModel
import com.makentoshe.booruchan.postpreviewspage.PostPageFragmentViewModel
import com.makentoshe.booruchan.postsamples.PostsSampleFragmentViewModel
import com.makentoshe.booruchan.postsamples.model.SamplePageHorizontalScrollBlockController
import com.makentoshe.booruchan.postsamplespage.PostSamplePageViewModel
import com.makentoshe.booruchan.postsamplespageimage.PostSamplePageImageFragmentViewModel
import com.makentoshe.booruchan.postsamplespageinfo.PostSamplePageInfoFragmentViewModel
import com.makentoshe.booruchan.start.StartFragmentViewModel
import com.makentoshe.repository.FileImageRepository
import com.makentoshe.repository.ImageRepository
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.PreviewImageRepository
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
    open fun onCreateView(owner: Fragment) = Unit
}

abstract class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = build()
        if (modelClass == viewModel::class.java) return viewModel as T
        return super.create(modelClass)
    }

    abstract fun build(): ViewModel
}

class StartFragmentViewModelFactory : ViewModelFactory() {
    override fun build() = StartFragmentViewModel(Booruchan.INSTANCE.router, Booruchan.INSTANCE.booruList)
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
    private val sampleRepository: ImageRepository,
    private val requestPermissionController: RequestPermissionController
) : ViewModelFactory() {
    override fun build(): PostsSampleFragmentViewModel {
        val fileRepository = FileImageRepository(booru)
        val router = Booruchan.INSTANCE.router
        return PostsSampleFragmentViewModel(
            booru,
            position,
            postsRepository,
            sampleRepository,
            router,
            fileRepository,
            requestPermissionController
        )
    }
}

class PostSamplePageFragmentViewModelFactory(
    private val position: Int,
    private val samplePageHorizontalScrollController: SamplePageHorizontalScrollBlockController,
    private val sampleRepository: ImageRepository,
    private val postsRepository: PostsRepository
) : ViewModelFactory() {
    override fun build() = PostSamplePageViewModel(position, samplePageHorizontalScrollController, sampleRepository, postsRepository)
}

class PostSamplePageImageFragmentViewModelFactory(
    private val samplesRepository: ImageRepository,
    private val position: Int,
    private val postsRepository: PostsRepository
) : ViewModelFactory() {
    override fun build() = PostSamplePageImageFragmentViewModel(position, postsRepository, samplesRepository)
}

class PostSamplePageInfoFragmentViewModelFactory(
    private val position: Int,
    private val postsRepository: PostsRepository
) : ViewModelFactory() {
    override fun build() = PostSamplePageInfoFragmentViewModel(position, postsRepository)
}
