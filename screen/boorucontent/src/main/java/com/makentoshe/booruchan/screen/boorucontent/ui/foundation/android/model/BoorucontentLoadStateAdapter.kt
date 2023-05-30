package com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.makentoshe.booruchan.library.logging.screenLogInfo
import com.makentoshe.booruchan.library.resources.databinding.BoorucontentFooterItemBinding

class BoorucontentLoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<BoorucontentLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = BoorucontentFooterItemBinding.inflate(layoutInflater, parent, false)
        return LoadStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class LoadStateViewHolder(
        private val binding: BoorucontentFooterItemBinding,
        retry: () -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.boorucontentFooterItemRetryButton.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            screenLogInfo("LoadStateAdapter", "bind: $loadState")

            if (loadState is LoadState.Error) {
                boorucontentFooterItemMessage.text =
                    loadState.error.localizedMessage.takeIf { !it.isNullOrBlank() } ?: loadState.error.toString()
            }

            boorucontentFooterItemProgress.isVisible = loadState is LoadState.Loading
            boorucontentFooterItemMessage.isVisible = loadState is LoadState.Error
            boorucontentFooterItemRetryButton.isVisible = loadState is LoadState.Error
        }
    }

}