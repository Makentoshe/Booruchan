package com.makentoshe.booruchan.postsamplespageinfo

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.makentoshe.booruchan.Booruchan
import org.jetbrains.anko.*

class PostSamplePageInfoFragmentUi(
    private val viewModel: PostSamplePageInfoFragmentViewModel
) : AnkoComponent<PostSamplePageInfoFragment> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<PostSamplePageInfoFragment>): View = with(ui) {
        verticalLayout {
            backgroundColorResource = style.background.backgroundColorRes

            recyclerView {
                adapter = viewModel.getRecyclerAdapter()
                layoutManager = LinearLayoutManager(context)
                lparams(matchParent, matchParent)
            }
        }
    }

    private fun _LinearLayout.recyclerView(action: RecyclerView.() -> Unit) {
        addView(RecyclerView(context).apply(action))
    }

}
