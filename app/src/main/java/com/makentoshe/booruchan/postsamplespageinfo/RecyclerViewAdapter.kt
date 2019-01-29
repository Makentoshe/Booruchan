package com.makentoshe.booruchan.postsamplespageinfo

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(
    private val viewModel: PostSamplePageInfoFragmentViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecyclerViewHolderBuilder(viewModel).buildVH(parent)
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = Unit

}