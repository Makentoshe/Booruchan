package com.makentoshe.booruchan.postpreview.view

import android.content.res.Configuration
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.postpreview.PostPageFragmentViewModel
import org.jetbrains.anko.*

class PostPageFragmentUiContent(private val viewModel: PostPageFragmentViewModel) :
    AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        gridView {
            visibility = View.GONE
            gravity = Gravity.CENTER
            verticalSpacing = dip(10)
            when (resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> numColumns = 3
                Configuration.ORIENTATION_LANDSCAPE -> numColumns = 6
            }

            viewModel.addOnPostsReceiveListener { onPostsReceive(it) }
            setOnItemClickListener(::onGridElementClick)
            setOnItemLongClickListener(::onGridElementLongClick)
        }.lparams(matchParent, matchParent) {
            setMargins(0, dip(10), 0, 0)
        }
    }

    private fun GridView.onPostsReceive(result: DownloadResult<Posts>) {
        if (result.data != null) {
            viewModel.loadPreviews(result.data)
            adapter = viewModel.getGridAdapter(result.data)
            visibility = View.VISIBLE
        }
    }

    private fun onGridElementClick(parent: AdapterView<*>, view: View, position: Int, id: Long) =
        viewModel.navigateToPostDetailsScreen(position)

    private fun onGridElementLongClick(parent: AdapterView<*>, view: View, position: Int, id: Long) =
        viewModel.onGridElementLongClick(position)
}