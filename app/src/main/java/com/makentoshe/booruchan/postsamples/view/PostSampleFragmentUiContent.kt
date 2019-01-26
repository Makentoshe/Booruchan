package com.makentoshe.booruchan.postsamples.view

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.BlockableViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamples.PostsSampleFragmentViewModel
import com.makentoshe.booruchan.postsamples.model.SamplePageController
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent

class PostSampleFragmentUiContent(
    private val viewModel: PostsSampleFragmentViewModel,
    private val fragmentManager: FragmentManager
) : AnkoComponent<LinearLayout> {

    override fun createView(ui: AnkoContext<LinearLayout>): View = with(ui) {
        frameLayout {
            lparams(matchParent, matchParent)
            blockableViewPager {
                id = R.id.viewpager
                adapter = viewModel.getPagerAdapter(fragmentManager)
                if (currentItem == 0) currentItem = viewModel.startPosition
                commandSubscribe(::applyCommand)
            }
        }
    }

    private fun ViewGroup.blockableViewPager(action: BlockableViewPager.() -> Unit) {
        addView(BlockableViewPager(context).apply(action))
    }

    private fun BlockableViewPager.commandSubscribe(action: (SamplePageController.Command, BlockableViewPager) -> Unit) {
        viewModel.blockController.subscribe { action(it, this) }
    }

    private fun applyCommand(command: SamplePageController.Command, view: BlockableViewPager) {
        when (command) {
            SamplePageController.Command.BLOCK -> blockCommand(view)
            SamplePageController.Command.UNBLOCK -> unblockCommand(view)
            SamplePageController.Command.CLOSE -> closeCommand()
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