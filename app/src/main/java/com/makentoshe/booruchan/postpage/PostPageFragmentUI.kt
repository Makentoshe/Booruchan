package com.makentoshe.booruchan.postpage

import android.content.res.Configuration
import android.graphics.PorterDuff
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import com.makentoshe.booruchan.Booruchan
import org.jetbrains.anko.*
import org.jetbrains.anko.coroutines.experimental.asReference

class PostPageFragmentUI(
    private val viewModel: PostPageFragmentViewModel
) : AnkoComponent<PostPageFragment> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<PostPageFragment>) = with(ui) {
        relativeLayout {
//            progressBarLayout()
            textView {
                viewModel.subscribeOnPosts {
                    println(it.javaClass)
                    text = it.toString()
                }
            }
//            contentLayout(ui)
        }
    }

    private fun _RelativeLayout.progressBarLayout() {
        progressBar {
            isIndeterminate = true
            indeterminateDrawable.setColorFilter(style.toolbar.getPrimaryColor(context), PorterDuff.Mode.SRC_ATOP)
            viewModel.subscribeOnPosts {


                visibility = View.GONE
            }
        }.lparams {
            centerInParent()
        }
    }

    private fun _RelativeLayout.contentLayout(ui: AnkoContext<PostPageFragment>) {
        gridView {
            when (ui.owner.requireActivity().resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> numColumns = 3
                Configuration.ORIENTATION_LANDSCAPE -> numColumns = 6
            }
            gravity = Gravity.CENTER
            verticalSpacing = dip(10)
            setPadding(0, dip(10), 0, 0)
            viewModel.subscribeOnPosts {

            }
//            viewModel.uiController.subscribeOnAction {
//                onPostsDownloaded {
//                    Handler(Looper.getMainLooper()).post {
//                        adapter = GridViewAdapter(it.posts, viewModel.previewRepository)
//                    }
//                }
//            }
        }.lparams(matchParent, matchParent)
    }
}