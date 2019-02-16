package com.makentoshe.booruchan.postpreview

import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.postpreview.model.AdapterBuilder
import com.makentoshe.booruchan.postpreview.model.GridViewAdapter
import com.makentoshe.repository.Repository
import com.makentoshe.viewmodel.ViewModel
import io.reactivex.disposables.CompositeDisposable

class AdapterViewModel : ViewModel(), AdapterBuilder {

    private val disposables = CompositeDisposable()
    private lateinit var previewsRepository: Repository<Post, ByteArray>

    override fun buildGridAdapter(posts: Posts): BaseAdapter {
        return GridViewAdapter(posts, previewsRepository, this, disposables)
    }

    override fun onCreateView(owner: Fragment) {
        disposables.clear()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    class Factory(
        private val previewsRepository: Repository<Post, ByteArray>
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = AdapterViewModel()
            viewModel.previewsRepository = previewsRepository
            return viewModel as T
        }
    }
}