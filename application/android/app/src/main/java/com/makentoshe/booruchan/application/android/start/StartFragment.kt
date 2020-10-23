package com.makentoshe.booruchan.application.android.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.makentoshe.booruchan.application.android.R
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {

    companion object {
        fun build(): StartFragment {
            return StartFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment_start_recycler.adapter = StartRecyclerAdapter(listOf("Gelbooru", "Danbooru", "Custombooru"))
        val itemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.item_decorator_divider, requireContext().theme))
        fragment_start_recycler.addItemDecoration(itemDecoration)
    }

}

class StartRecyclerAdapter(private val list: List<String>) : RecyclerView.Adapter<StartRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartRecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_start_item, parent, false)
        return StartRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: StartRecyclerViewHolder, position: Int) {
        holder.primary.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class StartRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val primary: TextView = view.findViewById(R.id.fragment_start_item_primary)
}