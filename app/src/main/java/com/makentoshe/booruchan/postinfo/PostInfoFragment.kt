package com.makentoshe.booruchan.postinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.io.Serializable

class PostInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostInfoUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.find<View>(R.id.toolbar_back).onClick {
            Booruchan.INSTANCE.router.exit()
        }
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        private const val POSITION = "Position"

        fun create(booru: Booru, tags: Set<Tag>, position: Int) = PostInfoFragment().apply {
            arguments = Bundle().apply {
                putSerializable(PostInfoFragment.BOORU, booru)
                putSerializable(PostInfoFragment.TAGS, tags as Serializable)
                putInt(PostInfoFragment.POSITION, position)
            }
        }
    }
}

