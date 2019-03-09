package com.makentoshe.booruchan.screen.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.screen.arguments
import com.makentoshe.booruchan.screen.samples.view.SamplePageUi
import org.jetbrains.anko.AnkoContext

class SamplePageFragment : Fragment() {

    private var position: Int
        get() = arguments!!.getInt(POSITION)
        set(value) = arguments().putInt(POSITION, value)

//    private val asyncRepositoryAccess by lazy {
//        val cache = PostInternalCache(requireContext())
//        val source = PostsRepository(booru)
//        val repository = CachedRepository(cache, source)
//        AsyncRepositoryAccess(repository).apply {
//            request(booruRequestBuilder.build(12))
//        }
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SamplePageUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (view as TextView).text = position.toString()
    }

    companion object {
        private const val POSITION = "Position"
        fun create(position: Int) = SamplePageFragment().apply {
            this.position = position
        }
    }
}