package com.makentoshe.booruchan.screen.sampleinfo.model

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.util.Consumer
import com.makentoshe.booruchan.api.Post

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
        val idstr = context.getString(com.makentoshe.booruchan.R.string.id)
        return StringBuilder(idstr).append(sep).append(post.id).toString()
    }

    private fun buildScoreString(context: Context): String {
        val scorestr = context.getString(com.makentoshe.booruchan.R.string.score)
        return StringBuilder(scorestr).append(sep).append(post.score).toString()
    }

    private fun buildRatingString(context: Context): String {
        val ratingstr = context.getString(com.makentoshe.booruchan.R.string.rating)
        return StringBuilder(ratingstr).append(sep).append(post.rating?.name?.toLowerCase()).toString()
    }
}