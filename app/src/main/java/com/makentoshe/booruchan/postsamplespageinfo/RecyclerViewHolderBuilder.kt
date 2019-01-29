package com.makentoshe.booruchan.postsamplespageinfo

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.AnkoContext

class RecyclerViewHolderBuilder(private val viewModel: PostSamplePageInfoFragmentViewModel) {

    fun buildVH(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = ViewHolderUi(viewModel).createView(AnkoContext.create(parent.context, parent))
        return object : RecyclerView.ViewHolder(view) {}
    }
}