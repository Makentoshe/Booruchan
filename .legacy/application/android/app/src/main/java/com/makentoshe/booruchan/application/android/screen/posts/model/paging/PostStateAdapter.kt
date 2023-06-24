package com.makentoshe.booruchan.application.android.screen.posts.model.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.screen.posts.view.FooterViewHolder

class PostStateAdapter(private val adapter: PagingDataAdapter<*, *>): LoadStateAdapter<FooterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FooterViewHolder(inflater.inflate(R.layout.fragment_posts_footer, parent, false))
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        if (loadState.endOfPaginationReached) holder.contentEnd() else when(loadState) {
            is LoadState.Loading -> holder.loading()
            is LoadState.Error -> holder.error { adapter.retry() }
        }
    }
}