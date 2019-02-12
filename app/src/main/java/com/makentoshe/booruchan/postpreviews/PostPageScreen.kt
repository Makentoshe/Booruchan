package com.makentoshe.booruchan.postpreviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.booruchan.postpreview.PostPageFragment
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.PreviewImageRepository
import com.makentoshe.repository.Repository
import com.makentoshe.repository.SampleImageRepository

class PostPageScreen(
    private val booru: Booru,
    private val position: Int,
    private val postsRepository: Repository<Booru.PostRequest, Posts>,
    private val previewsRepository: Repository<String, ByteArray>,
    private val samplesRepository: SampleImageRepository
) : FragmentScreen() {
    override val fragment: Fragment
        get() = PostPageFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Booru::class.java.simpleName, booru)
                putInt(PostPageFragment::class.java.simpleName, position)
                putSerializable(PostsRepository::class.java.simpleName, postsRepository)
                putSerializable(PreviewImageRepository::class.java.simpleName, previewsRepository)
                putSerializable(samplesRepository::class.java.simpleName, samplesRepository)
            }
        }
}