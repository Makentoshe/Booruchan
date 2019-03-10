//package com.makentoshe.booruchan.posttags
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.makentoshe.booruapi.Booru
//import com.makentoshe.booruapi.Tag
//import com.makentoshe.booruchan.Booruchan
//import com.makentoshe.booruchan.R
//import com.makentoshe.booruchan.postpreviews.viewmodel.TagsViewModel
//import com.makentoshe.booruchan.posttags.model.PostTagsNavigator
//import com.makentoshe.booruchan.posttags.model.TagsBuildViewModel
//import com.makentoshe.booruchan.posttags.view.PostTagsUi
//import org.jetbrains.anko.AnkoContext
//import org.jetbrains.anko.find
//import org.jetbrains.anko.sdk27.coroutines.onClick
//import java.io.Serializable
//
//class PostTagsFragment : Fragment() {
//
//    private lateinit var tagsController: TagsBuildViewModel
//    private lateinit var searchController: TagsViewModel
//    private lateinit var navigator: PostTagsNavigator
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        val booru = arguments!!.get(BOORU) as Booru
//        val tags = arguments!!.get(TAGS) as Set<Tag>
//        val position = arguments!!.getInt(POSITION)
//
////        val postsRepository = CachedRepository(
////            PostInternalCache(requireContext(), "posts"),
////            PostsRepository(booru)
////        )
//
////        var factory: ViewModelProvider.NewInstanceFactory = TagsBuildViewModel.Factory(postsRepository, position, tags)
////        tagsController = ViewModelProviders.of(this, factory)[TagsBuildViewModel::class.java]
//
////        factory = TagsViewModel.Factory(setOf())
////        searchController = ViewModelProviders.of(this, factory)[TagsViewModel::class.java]
//
//        navigator = PostTagsNavigator(Booruchan.INSTANCE.router, booru)
//
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        tagsController.onCreateView(this)
//        searchController.onCreateView(this)
//
//        return PostTagsUi(tagsController, searchController, navigator)
//            .createView(AnkoContext.create(requireContext(), this))
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        view.find<View>(R.id.toolbar_back).onClick {
//            Booruchan.INSTANCE.router.exit()
//        }
//    }
//
//    companion object {
//        private const val BOORU = "Booru"
//        private const val TAGS = "Tags"
//        private const val POSITION = "Position"
//
//        fun create(booru: Booru, tags: Set<Tag>, position: Int): Fragment {
//            return PostTagsFragment().apply {
//                arguments = Bundle().apply {
//                    putSerializable(BOORU, booru)
//                    putSerializable(TAGS, tags as Serializable)
//                    putInt(POSITION, position)
//                }
//            }
//        }
//    }
//}
//
