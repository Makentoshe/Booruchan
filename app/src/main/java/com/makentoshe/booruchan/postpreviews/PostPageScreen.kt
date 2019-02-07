package com.makentoshe.booruchan.postpreviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.booruchan.postpreview.PostPageFragment
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.PreviewImageRepository

class PostPageScreen(
    private val booru: Booru,
    private val position: Int,
    private val postsRepository: PostsRepository,
    private val previewsRepository: PreviewImageRepository
) : FragmentScreen() {
    override val fragment: Fragment
        get() = PostPageFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Booru::class.java.simpleName, booru)
                putInt(PostPageFragment::class.java.simpleName, position)
                putSerializable(PostsRepository::class.java.simpleName, postsRepository)
                putSerializable(PreviewImageRepository::class.java.simpleName, previewsRepository)
            }
        }
}