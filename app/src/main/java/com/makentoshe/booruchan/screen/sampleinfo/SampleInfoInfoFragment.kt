package com.makentoshe.booruchan.screen.sampleinfo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.util.Consumer
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.arguments
import org.jetbrains.anko.*

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

class SampleInfoInfoUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        listView {
            id = R.id.sampleinfo_info_listview
        }
    }
}

class ListViewInflater(val post: Post) : Consumer<ListView> {

    private val sep = ": "

    override fun accept(view: ListView) {
        val context = view.context
        val list = ArrayList<String>()
        list.add(buildIdString(context))
        list.add(buildScoreString(context))
        list.add(buildRatingString(context))
        view.adapter = ArrayAdapter<String>(view.context, android.R.layout.simple_list_item_1, list)
    }

    private fun buildIdString(context: Context): String {
        val idstr = context.getString(R.string.id)
        return StringBuilder(idstr).append(sep).append(post.id).toString()
    }

    private fun buildScoreString(context: Context): String {
        val scorestr = context.getString(R.string.score)
        return StringBuilder(scorestr).append(sep).append(post.score).toString()
    }

    private fun buildRatingString(context: Context): String {
        val ratingstr = context.getString(R.string.rating)
        return StringBuilder(ratingstr).append(sep).append(post.rating?.name?.toLowerCase()).toString()
    }
}