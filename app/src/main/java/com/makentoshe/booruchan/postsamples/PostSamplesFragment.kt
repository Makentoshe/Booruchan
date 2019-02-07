package com.makentoshe.booruchan.postsamples

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.Fragment
import com.makentoshe.repository.PostsRepository
import org.jetbrains.anko.backgroundColor

class PostSamplesFragment: Fragment<PostSamplesViewModel>() {

    override fun buildViewModel(arguments: Bundle): PostSamplesViewModel {
        val position = arguments.getInt(Int::class.java.simpleName)
        val holderArguments = ArgumentsHolder[this::class.java.simpleName.plus(position)]

        val postsRepository = holderArguments!![PostsRepository::class.java.simpleName] as PostsRepository

        val factory = PostSamplesViewModel.Factory(position, postsRepository)
        return ViewModelProviders.of(this, factory)[PostSamplesViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return View(context).apply {
            backgroundColor = Color.CYAN
        }
    }

    override fun onDestroy() {
        val activity = activity
        val isChangingConfigurations = activity != null && activity.isChangingConfigurations
        if (!isChangingConfigurations) {
            val position = arguments!!.getInt(Int::class.java.simpleName)
            ArgumentsHolder.remove(this::class.java.simpleName.plus(position))
        }

        super.onDestroy()
    }
}

