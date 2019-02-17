package com.makentoshe.booruchan.postsamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.postsamples.view.PostSamplesUi
import org.jetbrains.anko.AnkoContext

class PostSamplesFragment : androidx.fragment.app.Fragment() {

    private lateinit var viewModel: PostSamplesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val pagePosition = arguments!!.getInt(PAGEPOSITION)
        val itemPosition = arguments!!.getInt(ITEMPOSITION)
        val booru = arguments!!.get(BOORU) as Booru

        val router = Booruchan.INSTANCE.router
        val factory = PostSamplesViewModel.Factory(
            pagePosition,
            itemPosition,
            router,
            booru
        )
        viewModel = ViewModelProviders.of(this, factory)[PostSamplesViewModel::class.java]

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.onCreateView(this)
        return PostSamplesUi(viewModel).createView(AnkoContext.create(requireContext(), this))
    }

    companion object {
        private const val ITEMPOSITION = "ItemPosition"
        private const val PAGEPOSITION = "PagePosition"
        private const val BOORU = "Booru"
        fun create(itemPosition: Int, position: Int, booru: Booru): androidx.fragment.app.Fragment {
            return PostSamplesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ITEMPOSITION, itemPosition)
                    putInt(PAGEPOSITION, position)
                    putSerializable(BOORU, booru)
                }
            }
        }
    }
}

