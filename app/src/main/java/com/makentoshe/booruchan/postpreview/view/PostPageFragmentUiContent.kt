package com.makentoshe.booruchan.postpreview.view

import android.content.res.Configuration
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.postpreview.model.AdapterBuilder
import com.makentoshe.booruchan.postpreview.model.NavigationController
import com.makentoshe.booruchan.postpreview.model.PostsDownloadController
import com.makentoshe.controllers.DownloadResult
import org.jetbrains.anko.*

class PostPageFragmentUiContent(
    private val postsDownloadController: PostsDownloadController,
    private val adapterBuilder: AdapterBuilder,
    private val navigationController: NavigationController
) : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        gridView {
            visibility = View.GONE
            gravity = Gravity.CENTER
            verticalSpacing = dip(10)
            when (resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> numColumns = 3
                Configuration.ORIENTATION_LANDSCAPE -> numColumns = 6
            }

            postsDownloadController.addOnPostsReceiveListener { onPostsReceive(it) }
            setOnItemClickListener(::onGridElementClick)
            setOnItemLongClickListener(::onGridElementLongClick)
        }.lparams(matchParent, matchParent) {
            setMargins(0, dip(10), 0, 0)
        }
    }

    private fun GridView.onPostsReceive(result: DownloadResult<Posts>) {
        if (result.hasData()) {
            adapter = adapterBuilder.buildGridAdapter(result.data)
            visibility = View.VISIBLE
        }
    }

    private fun onGridElementClick(parent: AdapterView<*>, view: View, position: Int, id: Long) =
        navigationController.onSampleScreenNavigate(position)

    private fun onGridElementLongClick(parent: AdapterView<*>, view: View, position: Int, id: Long) = true
//        viewModel.onGridElementLongClick(position)
}