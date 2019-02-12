package com.makentoshe.booruchan.postpreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Fragment
import com.makentoshe.booruchan.postpreview.view.PostPageFragmentUi
import com.makentoshe.repository.Repository
import com.makentoshe.repository.SampleImageRepository
import org.jetbrains.anko.AnkoContext

class PostPageFragment : Fragment<PostPageFragmentViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return PostPageFragmentUi(viewModel)
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun buildViewModel(arguments: Bundle): PostPageFragmentViewModel {
        val position = arguments.getInt(PostPageFragment::class.java.simpleName)

        val arguments = Companion.arguments[position]!!

        val factory = PostPageFragmentViewModel.Factory(
            arguments.booru,
            position,
            arguments.postsRepository,
            arguments.previewsRepository,
            arguments.samplesRepository,
            arguments.tags
        )
        return ViewModelProviders.of(this, factory)[PostPageFragmentViewModel::class.java]
    }

    companion object {
        fun create(
            booru: Booru,
            position: Int,
            postsRepository: Repository<Booru.PostRequest, Posts>,
            previewsRepository: Repository<String, ByteArray>,
            samplesRepository: SampleImageRepository,
            tags: Set<Tag>
        ): androidx.fragment.app.Fragment {

            arguments[position] = ArgumentsHolder(
                booru, postsRepository, previewsRepository, samplesRepository, tags
            )

            return PostPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(Int::class.java.simpleName, position)
                }
            }
        }

        private val arguments = HashMap<Int, ArgumentsHolder>()
    }

}

data class ArgumentsHolder(
    val booru: Booru,
    val postsRepository: Repository<Booru.PostRequest, Posts>,
    val previewsRepository: Repository<String, ByteArray>,
    val samplesRepository: SampleImageRepository,
    val tags: Set<Tag>
)