package com.makentoshe.booruchan.screen.sampleinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.sampleinfo.model.ListViewInflater
import com.makentoshe.booruchan.screen.sampleinfo.view.SampleInfoInfoUi
import org.jetbrains.anko.*

class SampleInfoInfoFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SampleInfoInfoUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listview = view.find<ListView>(R.id.sampleinfo_info_listview)
        ListViewInflater(post).accept(listview)
    }

    companion object {
        private const val BOORU = "Booru"
        private const val POST = "Post"
        fun create(booru: Booru, post: Post) = SampleInfoInfoFragment().apply {
            this.booru = booru
            this.post = post
        }
    }
}