package com.makentoshe.booruchan.postpage

import android.content.res.Configuration
import android.graphics.PorterDuff
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import com.makentoshe.booruchan.Booruchan
import org.jetbrains.anko.*

class PostPageFragmentUI(
    private val viewModel: PostPageFragmentViewModel
) : AnkoComponent<PostPageFragment> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<PostPageFragment>) = with(ui) {
        relativeLayout {
            progressBarLayout()
            contentLayout(ui)
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
            viewModel.subscribeOnPosts {
                if (it.data != null) {
                    adapter = viewModel.getGridAdapter(it.data)
                }
            }
            setOnItemClickListener(::onGridElementClick)
            setOnItemLongClickListener(::onGridElementLongClick)
        }.lparams(matchParent, matchParent) {
            setMargins(0, dip(10), 0, 0)
        }
    }

    private fun onGridElementClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        viewModel.navigateToPostDetailsScreen(position)
    }

    private fun onGridElementLongClick(parent: AdapterView<*>, view: View, position: Int, id: Long): Boolean {
        return viewModel.onGridElementLongClick(position)
    }
}