package com.makentoshe.booruchan.application.android.screen.search.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.makentoshe.booruchan.application.android.common.getColor
import com.makentoshe.booruchan.application.core.arena.tag.TagsArena
import com.makentoshe.booruchan.core.tag.Tag
import com.makentoshe.booruchan.core.tag.context.TagsContext
import com.makentoshe.booruchan.core.tag.deserialize.TagsDeserialize
import com.makentoshe.booruchan.core.tag.network.TagsFilter
import com.makentoshe.booruchan.core.tag.network.TagsRequest
import kotlinx.coroutines.runBlocking

class PostsSearchTagsAutocompleteAdapter(
    private val context: Context,
    private val arena: TagsArena,
    private val tagsContext: TagsContext<TagsRequest, TagsFilter>
) : BaseAdapter(), Filterable {

    private val tags = ArrayList<Tag>()

    override fun getItem(position: Int) = tags[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_dropdown_item_1line, parent, false)

        val textView = view as TextView
        textView.text = getItem(position).text
        textView.setTextColor(getItem(position).getColor(context))

        return view
    }

    override fun getCount() = tags.size

    override fun getFilter() = object : Filter() {

        override fun performFiltering(constraint: CharSequence?) = FilterResults().apply {
            if (constraint?.isNotBlank() != true) return@apply
            suspendPerformFiltering(constraint)
        }

        private fun FilterResults.suspendPerformFiltering(constraint: CharSequence) = runBlocking {
            val filterBuilder = tagsContext.filterBuilder()
            // TODO add DESCENDING ORDER
            // TODO add sort by posts counts
            val count = filterBuilder.count.build("10")
            val starts = filterBuilder.starts.build(constraint.toString())
            val response = arena.suspendFetch(filterBuilder.build(count, starts))
            if (response.isSuccess) {
                this@suspendPerformFiltering.count = 10
                values = response.getOrNull()!!
            } else {
                throw response.exceptionOrNull()!!
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.count ?: -1 > 0) {
                tags.clear()
                tags.addAll((results?.values as TagsDeserialize<Tag>).tags)
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }
    }
}