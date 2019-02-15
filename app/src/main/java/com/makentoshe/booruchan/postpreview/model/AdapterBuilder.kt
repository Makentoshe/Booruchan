package com.makentoshe.booruchan.postpreview.model

import android.widget.BaseAdapter
import com.makentoshe.booruapi.Posts

interface AdapterBuilder {
    fun buildGridAdapter(posts: Posts): BaseAdapter
}