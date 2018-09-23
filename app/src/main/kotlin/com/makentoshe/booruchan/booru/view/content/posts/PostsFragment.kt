package com.makentoshe.booruchan.booru.view.content.posts

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.booru.model.content.posts.PostsContentViewModel
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.settings.application.AppSettings
import org.jetbrains.anko.AnkoContext

class PostsFragment : Fragment() {

    lateinit var viewModel: PostsContentViewModel
    lateinit var ui: PostsFragmentUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val booru = arguments?.getSerializable(booruArg) as Boor
        val appSettings = arguments?.getSerializable(appSettingsArg) as AppSettings
        val factory = PostsContentViewModel.Factory(booru, appSettings)
        viewModel = ViewModelProviders.of(this, factory)[PostsContentViewModel::class.java]
        println("Create ${this::class.java.simpleName}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = PostsFragmentUI(viewModel)
        return ui.createView(AnkoContext.create(activity!!, this))
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Destroy ${this::class.java.simpleName}")
    }

    companion object {

        private const val booruArg = "BooruArg"
        private const val appSettingsArg = "AppSettingsArg"

        fun new(booru: Boor, appSettings: AppSettings): PostsFragment {
            val fragment = PostsFragment()
            val args = Bundle()
            args.putSerializable(booruArg, booru)
            args.putSerializable(appSettingsArg, appSettings)
            fragment.arguments = args
            return fragment
        }
    }
}