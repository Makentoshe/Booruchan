package com.makentoshe.booruchan.screen.sampleinfo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.sampleinfo.model.PostMapper
import com.makentoshe.booruchan.screen.sampleinfo.view.SampleInfoInfoUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class SampleInfoInfoFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SampleInfoInfoUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listview = view.find<ListView>(R.id.sampleinfo_info_listview)
        setupAdapter(listview)
        listview.setOnItemLongClickListener(::onItemClick)
    }

    private fun setupAdapter(listView: ListView): ArrayList<String> {
        val mapper = PostMapper()

        val data = ArrayList<String>()
        data.add(mapper.mapToPostId(post, requireContext()))
        data.add(mapper.mapToPostScore(post, requireContext()))
        data.add(mapper.mapToRating(post, requireContext()))
        data.add(mapper.mapToSource(post, requireContext()))

        listView.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, data)
        return data
    }

    private fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long): Boolean {
        //get data
        val data = parent.getItemAtPosition(position) as String
        //if data contains source - click should start third party app (browser)
        if (data.contains(post.source) && post.source.isNotBlank()) {
            startBrowser(post.source)
            return true
        }
        return false
    }

    private fun startBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
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