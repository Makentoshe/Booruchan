package com.makentoshe.booruchan.application.android.start.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.start.navigation.StartNavigation
import com.makentoshe.booruchan.application.android.start.view.StartRecyclerViewHolder
import context.BooruContext

class StartRecyclerAdapter(
    private val list: List<BooruContext>,
    private val navigation: StartNavigation
) : RecyclerView.Adapter<StartRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartRecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_start_item, parent, false)
        return StartRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: StartRecyclerViewHolder, position: Int) {
        val booruContext = list[position]
        holder.primary.text = booruContext.title
        holder.secondary.text = booruContext.url
        holder.itemView.setOnClickListener { navigation.navigateToBooruScreen(booruContext) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}