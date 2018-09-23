package com.makentoshe.booruchan.booru.model.content.posts

import android.annotation.SuppressLint
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.model.content.ContentViewModel
import com.makentoshe.booruchan.booru.view.content.PostsFragment
import com.makentoshe.booruchan.common.forLollipop
import com.makentoshe.booruchan.common.settings.application.AppSettings
import es.dmoral.toasty.Toasty
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PostsContent(private val appSettings: AppSettings) : Content {

    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton

    override fun createView(contentViewModel: ContentViewModel): Fragment {
        return PostsFragment()
    }

    override fun onSearchStarted(): (String?) -> (Unit) {
        return {}
    }

}