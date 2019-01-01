package com.makentoshe.booruchan.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.makentoshe.booruapi.Tag

class DelayAutocompleteAdapter(private val repository: DelayAutocompleteRepository) : BaseAdapter(), Filterable {

    private var tags: List<Tag> = listOf()

    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            if (constraint != null) {
                val tags = repository.get(constraint)!!
                results.values = tags
                results.count = tags.size
            }
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results != null && results.count > 0) {
                tags = results.values as List<Tag>
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val root = convertView ?: LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_dropdown_item_1line, parent, false)
        (root as TextView).text = getItem(position).name
        return root
    }

    override fun getItem(position: Int) = tags[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = tags.size
}


