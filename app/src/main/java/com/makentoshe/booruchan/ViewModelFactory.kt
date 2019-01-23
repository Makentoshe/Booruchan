package com.makentoshe.booruchan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.booru.BooruFragmentViewModel
import com.makentoshe.booruchan.booru.DrawerController
import com.makentoshe.booruchan.postpage.PostPageFragmentViewModel
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import com.makentoshe.booruchan.posts.model.PostsRepository
import com.makentoshe.booruchan.posts.model.PreviewsRepository
import com.makentoshe.booruchan.postsamples.PostsSampleFragmentViewModel
import com.makentoshe.booruchan.postsamples.model.SamplePageController
import com.makentoshe.booruchan.postsamplespage.PostSamplePageViewModel
import com.makentoshe.booruchan.postsamplespageimage.PostSamplePagePreviewFragmentViewModel
import com.makentoshe.booruchan.start.StartFragmentViewModel

class ViewModelFactory(
    private val booru: Booru? = null,
    private val drawerController: DrawerController? = null,
    private val position: Int? = null,
    private val postsRepository: PostsRepository? = null,
    private val previewsRepository: PreviewsRepository? = null,
    private val tags: Set<Tag>? = null,
    private val samplePageController: SamplePageController? = null,
    private val sampleRepository: ImageRepository? = null
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {

            StartFragmentViewModel::class.java -> {
                StartFragmentViewModel() as T
            }

            BooruFragmentViewModel::class.java -> {
                BooruFragmentViewModel(booru!!, tags!!) as T
            }

            PostsFragmentViewModel::class.java -> {
                PostsFragmentViewModel(booru!!, drawerController!!, tags!!) as T
            }

            PostPageFragmentViewModel::class.java -> {
                PostPageFragmentViewModel(booru!!, position!!, postsRepository!!, previewsRepository!!) as T
            }

            PostsSampleFragmentViewModel::class.java -> {
                PostsSampleFragmentViewModel(booru!!, position!!, postsRepository!!, sampleRepository!!) as T
            }

            PostSamplePageViewModel::class.java -> {
                PostSamplePageViewModel(position!!, samplePageController!!, sampleRepository!!, postsRepository!!) as T
            }

            PostSamplePagePreviewFragmentViewModel::class.java -> {
                PostSamplePagePreviewFragmentViewModel(sampleRepository!!, position!!, postsRepository!!) as T
            }

            else -> super.create(modelClass)
        }
    }
}