package com.makentoshe.booruchan.application.android.screen.search.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.makentoshe.booruchan.core.Text
import com.makentoshe.booruchan.core.text

class PostsSearchTagsAutocompleteAdapter(private val context: Context) : BaseAdapter(), Filterable {

    private val tags = ArrayList<Text>()

    override fun getItem(position: Int) = tags[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_dropdown_item_1line, parent, false)
        (view as TextView).text = getItem(position).text
        return view
    }

    override fun getCount() = tags.size

    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?) = FilterResults().apply {
            if (constraint?.isNotBlank() != true) return@apply
            // TODO networking
            count = 2
            values = listOf(text("sas"), text("asa"), text("anus"), text("psa"))
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.count ?: -1 > 0) {
                tags.clear()
                tags.addAll(results!!.values as List<Text>)
            } else {
                notifyDataSetInvalidated()
            }
        }
    }
}