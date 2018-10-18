package com.makentoshe.booruchan.booru.content.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.HttpClient
import kotlinx.coroutines.experimental.runBlocking

class AutocompleteAdapter(private val context: Context, private val boor: Boor) : BaseAdapter(), Filterable {

    private var tips = ArrayList<String>()

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults = runBlocking {
                val results = FilterResults()
                if (constraint != null) {
                    val tips = boor.getAutocompleteSearchVariations(split(constraint.toString()))
                    results.values = tips
                    results.count = tips.size
                }
                return@runBlocking results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    tips = results.values as ArrayList<String>
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }

    private fun split(string: String): String {
        if (string.endsWith(" ")) return ""
        val list = string.split(" ".toRegex())
        return list[list.size - 1]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var root = convertView
        if (root == null) {
            val inflater = LayoutInflater.from(context)
            root = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false)
        }
        val string = getItem(position)
        (root as TextView).text = string
        return root
    }

    override fun getItem(position: Int): String {
        return tips[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return tips.size
    }

}
