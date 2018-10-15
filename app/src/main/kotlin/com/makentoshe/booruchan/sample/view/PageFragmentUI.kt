package com.makentoshe.booruchan.sample.view

import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import com.makentoshe.booruchan.common.api.entity.Post
import com.makentoshe.booruchan.common.runOnUi
import com.makentoshe.booruchan.sample.PageViewModel
import com.makentoshe.booruchan.sample.SampleViewModel
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.*

class PageFragmentUI(private val viewModel: PageViewModel,
                     private val sampleViewModel: SampleViewModel) : AnkoComponent<PageFragment> {

    private lateinit var post: Post
    //todo переместить поле post из метода и так же в обсервере проверять, если пост уже загружен и этот фрагмент текущий - обновить данные

    override fun createView(ui: AnkoContext<PageFragment>): View = with(ui) {
        verticalLayout {
            val progressBar = horizontalProgressBar {
                isIndeterminate = true
            }.lparams(width = matchParent, height = dip(16))

            createImageView(progressBar, ui)
        }
    }

    private fun _LinearLayout.createImageView(progressBar: ProgressBar, ui: AnkoContext<PageFragment>) {
        imageView {
            var isLoaded = false
            sampleViewModel.setPageObserver(ui.owner) { it, context ->
                if (it == viewModel.position) {
                    if (!isLoaded) {
                        CoroutineScope(context).launch {
                            if (!this@PageFragmentUI::post.isInitialized) post = viewModel.loadPostData()
                            runOnUi { sampleViewModel.setPost(post) }
                            val bitmap = viewModel.loadPostImage(post)
                            runOnUi {
                                setImageBitmap(bitmap)
                                progressBar.visibility = View.GONE
                                isLoaded = true
                            }
                        }
                    } else {
                        if (!this@PageFragmentUI::post.isInitialized) return@setPageObserver
                        runOnUi { sampleViewModel.setPost(post) }
                    }
                }
            }
        }.lparams(matchParent, matchParent) {
            setMargins(dip(8), dip(8), dip(8), dip(72))
        }
    }
}