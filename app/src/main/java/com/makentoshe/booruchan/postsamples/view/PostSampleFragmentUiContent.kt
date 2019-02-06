package com.makentoshe.booruchan.postsamples.view

import android.Manifest
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.makentoshe.booruchan.BlockableViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamples.PostsSampleFragmentViewModel
import com.makentoshe.booruchan.postsamples.model.SamplePageHorizontalScrollBlockController
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.onPageChangeListener

class PostSampleFragmentUiContent(
    private val viewModel: PostsSampleFragmentViewModel,
    private val fragmentManager: FragmentManager
) : AnkoComponent<LinearLayout> {

    override fun createView(ui: AnkoContext<LinearLayout>): View = with(ui) {
        frameLayout {
            lparams(matchParent, matchParent)
            blockableViewPager {
                id = R.id.postsample_content_viewpager
                adapter = viewModel.getPagerAdapter(fragmentManager)
                if (currentItem == 0) currentItem = viewModel.selectedPage
                commandSubscribe(::applyCommand)
                onPageChangeListener { onPageSelected { viewModel.selectedPage = it } }
            }
            viewModel.onFileDownloadListener {
                if (it == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                    return@onFileDownloadListener viewModel.requestPermission(it)
                }
                val message = "File $it has been downloaded."
                Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun ViewGroup.blockableViewPager(action: BlockableViewPager.() -> Unit) {
        addView(BlockableViewPager(context).apply(action))
    }

    private fun BlockableViewPager.commandSubscribe(action: (SamplePageHorizontalScrollBlockController.Command, BlockableViewPager) -> Unit) {
        viewModel.onNewPageBlockCommandListener{ action(it, this) }
    }

    private fun applyCommand(command: SamplePageHorizontalScrollBlockController.Command, view: BlockableViewPager) {
        when (command) {
            SamplePageHorizontalScrollBlockController.Command.BLOCK -> blockCommand(view)
            SamplePageHorizontalScrollBlockController.Command.UNBLOCK -> unblockCommand(view)
            SamplePageHorizontalScrollBlockController.Command.CLOSE -> closeCommand()
        }
    }

    private fun blockCommand(view: BlockableViewPager) {
        view.isBlocked = true
    }

    private fun unblockCommand(view: BlockableViewPager) {
        view.isBlocked = false
    }

    private fun closeCommand() = viewModel.backToPreviews()
}