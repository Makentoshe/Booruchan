package com.makentoshe.booruchan.booru.model.gallery.post.ordinf

import android.arch.lifecycle.ViewModel
import android.support.v7.widget.RecyclerView

class PostOrderedInfinityViewModel: ViewModel() {

    fun getGalleryAdapter(): RecyclerView.Adapter<*>? {
        return PostOrderedInfinityAdapter()
    }
}