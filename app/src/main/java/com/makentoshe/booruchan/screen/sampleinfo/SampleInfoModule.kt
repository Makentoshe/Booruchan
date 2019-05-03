package com.makentoshe.booruchan.screen.sampleinfo

import android.view.View
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadListener
import com.makentoshe.booruchan.screen.sampleinfo.controller.SampleInfoTagsViewController
import com.makentoshe.booruchan.screen.sampleinfo.controller.SampleInfoViewController
import com.makentoshe.booruchan.screen.sampleinfo.fragment.SampleInfoFragment
import com.makentoshe.booruchan.screen.sampleinfo.fragment.SampleInfoTagsFragment
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object SampleInfoModule {

    val module = module {
        viewModel { (b: Booru, t: Set<Tag>, p: Int, d: CompositeDisposable) ->
            SampleInfoViewModel(b, t, p, d)
        }

        scope(named<SampleInfoFragment>()) {
            scoped { (v: View, l: PostsDownloadListener, b: Booru, i: Int) ->
                SampleInfoViewController(v, l, b, i)
            }
        }

        scope(named<SampleInfoTagsFragment>()) {
            scoped { (v: View, b: Booru, p: Post) -> SampleInfoTagsViewController(v, b, p) }
        }
    }
}