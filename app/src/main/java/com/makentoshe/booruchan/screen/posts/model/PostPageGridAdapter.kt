package com.makentoshe.booruchan.screen.posts.model

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.repository.Repository
import com.makentoshe.booruchan.repository.PreviewRxRepository
import com.makentoshe.booruchan.screen.posts.view.PostPageGridElement
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.GlobalScope
import org.jetbrains.anko.AnkoContext

class PostPageGridAdapter(
    private val context: Context,
    private val posts: List<Post>
//    repository: Repository<Post, ByteArray>,
//    private val disposables: CompositeDisposable
) : BaseAdapter() {

    private val repositoryList = ArrayList<PreviewRxRepository>()
//
//    init {
//        posts.forEach {
//            val repo = PreviewRxRepository(
//                repository,
//                GlobalScope.coroutineContext
//            )
//            repo.request(it)
//            repositoryList.add(repo)
//        }
//    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return convertView ?: PostPageGridElement(
//            repositoryList[position],
//            disposables
        ).createView(AnkoContext.create(context))
    }

    override fun getItem(position: Int) = posts[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = posts.size
}