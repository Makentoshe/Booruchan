package com.makentoshe.booruchan.postsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.Fragment
import com.makentoshe.viewmodel.ViewModel
import org.jetbrains.anko.backgroundColor
import kotlin.random.Random

class PostSampleFragment : Fragment<PostSampleViewModel>() {

    override fun buildViewModel(arguments: Bundle): PostSampleViewModel {
        val position = arguments.getInt(Int::class.java.simpleName)
        val factory = PostSampleViewModel.Factory(position)
        return ViewModelProviders.of(this, factory)[PostSampleViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(context).apply {
            text = viewModel.position.toString()
            backgroundColor = Random.nextInt()
        }
    }
}

class PostSampleViewModel private constructor() : ViewModel() {
    var position = 0
        private set

    class Factory(
        private val position: Int
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSampleViewModel()
            viewModel.position = position
            return viewModel as T
        }
    }
}