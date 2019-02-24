package com.makentoshe.booruchan.posttags.model

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruapi.Tag
import com.makentoshe.controllers.DownloadResult
import com.makentoshe.controllers.DownloadRxController
import com.makentoshe.repository.Repository
import com.makentoshe.viewmodel.ViewModel
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope

class TagsBuildViewModel private constructor() : ViewModel(), TagsBuildController {

    private lateinit var tagSetRxController: TagSetRxController

    override fun onCreateView(owner: Fragment) {
        tagSetRxController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        tagSetRxController.clear()
    }

    override fun onTagsReceivedListener(action: (DownloadResult<Set<Tag>>) -> Unit) {
        tagSetRxController.subscribe {
            Handler(Looper.getMainLooper()).post { action(it) }
        }
    }

    fun getPostTags(position: Int, tags: Set<Tag> = setOf()) {
        tagSetRxController.action(Booru.PostRequest(1, position, tags))
    }

    class Factory(
        private val postsRepository: Repository<Booru.PostRequest, Posts>,
        private val position: Int,
        private val tags: Set<Tag>
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = TagsBuildViewModel()
            viewModel.tagSetRxController = TagSetRxController(postsRepository, viewModel)

            viewModel.getPostTags(position, tags)

            return viewModel as T
        }
    }


    private class TagSetRxController(
        private val repository: Repository<Booru.PostRequest, Posts>,
        coroutineScope: CoroutineScope
    ) : DownloadRxController<Set<Tag>, Booru.PostRequest>(BehaviorSubject.create(), coroutineScope) {

        override fun performDownload(param: Booru.PostRequest): Set<Tag> {
            return repository.get(param)!![0].tags.toSet()
        }
    }

}
