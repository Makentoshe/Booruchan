package com.makentoshe.booruchan.sample

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.support.v7.widget.Toolbar
import com.makentoshe.booruchan.common.BackdropView
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import com.makentoshe.booruchan.common.styles.Style
import com.makentoshe.booruchan.sample.model.IconAnimator

class SampleViewModel(@JvmField val booru: Boor, val pageId: Int) : ViewModel() {

    private val toolbarMenuIcon = IconAnimator()

    fun setupActionBarController(actionBar: Toolbar, backdrop: BackdropView, style: Style) {
        var state = BackdropView.State.EXPANDED
        var block = false
        backdrop.addStateListener {
            state = it
            block = false
        }
        actionBar.setNavigationOnClickListener {
            if (block) return@setNavigationOnClickListener
            block = true
            if (state == BackdropView.State.EXPANDED) {
                backdrop.collapse()
                toolbarMenuIcon.toCross(actionBar, style)
            }
            if (state == BackdropView.State.COLLAPSED) {
                backdrop.expand()
                toolbarMenuIcon.toMenu(actionBar, style)
            }
        }
    }

    class Factory(private val intent: Intent) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == SampleViewModel::class.java) {
//                val booru = intent.getSerializableExtra(BOORU_EXTRA) as Boor
//                val pageId = intent.getIntExtra(START_ID, 0)
                return SampleViewModel(Gelbooru(), 3) as T
            }
            return super.create(modelClass)
        }

    }

}