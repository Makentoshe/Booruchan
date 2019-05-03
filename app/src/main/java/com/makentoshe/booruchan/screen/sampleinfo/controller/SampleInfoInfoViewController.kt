package com.makentoshe.booruchan.screen.sampleinfo.controller

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.screen.sampleinfo.model.PostMapper
import com.makentoshe.booruchan.screen.sampleinfo.model.SampleInfoInfoSourceLongClick
import org.jetbrains.anko.find

class SampleInfoInfoViewController(private val root: View, private val post: Post) {

    private val listview by lazy { root.find<ListView>(R.id.sampleinfo_info_listview) }

    fun bind(fragment: Fragment) {
        listview.adapter = buildAdapter(fragment.requireContext())
        listview.onItemLongClickListener = SampleInfoInfoSourceLongClick(post)
    }

    private fun buildDataList(context: Context): ArrayList<String> {
        val mapper = PostMapper()

        val data = ArrayList<String>()
        data.add(mapper.mapToPostId(post, context))
        data.add(mapper.mapToPostScore(post, context))
        data.add(mapper.mapToRating(post, context))
        data.add(mapper.mapToSource(post, context))

        return data
    }

    private fun buildAdapter(context: Context): BaseAdapter {
        val list = buildDataList(context)
        return ArrayAdapter(context, android.R.layout.simple_list_item_1, list)
    }

}