package com.makentoshe.booruchan.sample

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.support.v7.widget.Toolbar
import com.makentoshe.booruchan.common.BackdropView
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.entity.Post
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import com.makentoshe.booruchan.common.styles.Style
import com.makentoshe.booruchan.sample.model.IconAnimator
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.cancel
import kotlin.coroutines.experimental.CoroutineContext

class SampleViewModel(@JvmField val booru: Boor, val pageId: Int, @JvmField val tagsString: String)
    : ViewModel() {

    private val toolbarMenuIcon = IconAnimator()
    private var currentPage = MutableLiveData<Int>()
    private var post = MutableLiveData<Post>()
    private var coroutineContext: CoroutineContext = GlobalScope.coroutineContext

    fun setCurrentPage(int: Int) {
        currentPage.value = int
    }

    fun setPageObserver(lifecycleOwner: LifecycleOwner, action: (Int, CoroutineContext) -> Unit) {
        coroutineContext.cancel()
        coroutineContext = GlobalScope.coroutineContext
        currentPage.observe(lifecycleOwner, Observer { action(it!!, coroutineContext) })
    }

    fun setupActionBarController(actionBar: Toolbar, backdrop: BackdropView, style: Style) {
        var state = BackdropView.State.EXPANDED
        var block = false
        backdrop.addStateListener {
            state = it
            block = false
        }
        actionBar.setNavigationOnClickListener {
            if (block) return@setNavigationOnClickListener
            block = true
            if (state == BackdropView.State.EXPANDED) {
                backdrop.collapse()
                toolbarMenuIcon.toCross(actionBar, style)
            }
            if (state == BackdropView.State.COLLAPSED) {
                backdrop.expand()
                toolbarMenuIcon.toMenu(actionBar, style)
            }
        }
    }

    fun setPost(post: Post) {
        this.post.value = post
    }

    fun setPostObserver(owner: LifecycleOwner, handler: (Post) -> Unit) {
        post.observe(owner, Observer { handler(it!!) })
    }

    class Factory(private val intent: Intent) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == SampleViewModel::class.java) {
//                val booru = intent.getSerializableExtra(BOORU_EXTRA) as Boor
//                val pageId = intent.getIntExtra(START_ID, 0)
//                val tags = intent.getStringExtra(TAGS_EXTRA)
                return SampleViewModel(Gelbooru(), 3, "hatsune_miku") as T
            }
            return super.create(modelClass)
        }

    }

}