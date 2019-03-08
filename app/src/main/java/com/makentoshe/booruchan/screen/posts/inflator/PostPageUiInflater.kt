package com.makentoshe.booruchan.screen.posts.inflator

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.GridView
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.Inflater
import com.makentoshe.booruchan.repository.AsyncRepository
import com.makentoshe.booruchan.repository.Repository
import com.makentoshe.booruchan.screen.posts.model.PostPageGridAdapter
import io.reactivex.disposables.CompositeDisposable

class PostPageUiInflater(
    private val asyncRepository: AsyncRepository<Booru.PostRequest, List<Post>>,
    private val previewsRepository: Repository<Post, ByteArray>,
    private val request: Booru.PostRequest,
    private val disposables: CompositeDisposable
) : Inflater {

    override fun inflate(view: View) {
        view as GridView
        val context = view.context

        asyncRepository.get(request) {
            Handler(Looper.getMainLooper()).post {
                view.adapter = PostPageGridAdapter(
                    context,
                    it!!,
                    previewsRepository,
                    disposables
                )
            }
        }
    }
}