package com.makentoshe.booruchan.screen.sampleinfo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import org.jetbrains.anko.backgroundColor

class SampleInfoCommentsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(requireContext()).apply {
            backgroundColor = Color.MAGENTA
            text = "Comments"
        }
    }

    companion object {
        fun create(booru: Booru, post: Post) = SampleInfoCommentsFragment()
    }
}