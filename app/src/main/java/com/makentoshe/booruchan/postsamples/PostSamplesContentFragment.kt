//package com.makentoshe.booruchan.postsamples
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProviders
//import com.makentoshe.booruapi.Booru
//import com.makentoshe.booruapi.Tag
//import com.makentoshe.booruchan.postsamples.model.AdapterBuilder
//import com.makentoshe.booruchan.postsamples.model.AdapterBuilderImpl
//import com.makentoshe.booruchan.postsamples.view.PostSamplesContentUi
//import org.jetbrains.anko.AnkoContext
//import java.io.Serializable
//
//class PostSamplesContentFragment : Fragment() {
//
//    private lateinit var viewModel: PostSamplesContentViewModel
//    private lateinit var adapterBuilder: AdapterBuilder
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        val booru = arguments!!.get(BOORU) as Booru
//        val tags = arguments!!.get(TAGS) as Set<Tag>
//        val position = arguments!!.getInt(POSITION)
//        val factory = PostSamplesContentViewModel.Factory(position, booru)
//        val fullScreenController = arguments!![FULLSCR] as FullScreenController
//
//        viewModel = ViewModelProviders.of(this, factory)[PostSamplesContentViewModel::class.java]
//
//        adapterBuilder = AdapterBuilderImpl(booru, tags, fullScreenController)
//
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        viewModel.onCreateView(this)
//
//        return PostSamplesContentUi(viewModel, adapterBuilder)
//            .createView(AnkoContext.create(requireContext(), this))
//    }
//
//    companion object {
//        private const val BOORU = "Booru"
//        private const val TAGS = "Tags"
//        private const val POSITION = "Position"
//        private const val FULLSCR = "FullScreenController"
//        fun create(
//            booru: Booru,
//            tags: Set<Tag>,
//            position: Int,
//            fullScreenController: FullScreenController
//        ): Fragment {
//            return PostSamplesContentFragment().apply {
//                arguments = Bundle().apply {
//                    putSerializable(BOORU, booru)
//                    putSerializable(TAGS, tags as Serializable)
//                    putSerializable(FULLSCR, fullScreenController)
//                    putInt(POSITION, position)
//                }
//            }
//        }
//    }
//}
//
