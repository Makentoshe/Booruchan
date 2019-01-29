package com.makentoshe.booruchan.postsamplespageinfo

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.chipGroup
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import kotlin.random.Random
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan._ChipGroup
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception


class ViewHolderUi(
    private val viewModel: PostSamplePageInfoFragmentViewModel
) : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        verticalLayout {

            addPostInfos().invokeOnCompletion { addPostTags() }

        }
    }

    private fun _LinearLayout.addPostInfos() = viewModel.launch {
        val downloadResultPost = viewModel.getPost().await()
        if (downloadResultPost.data != null) {
            Handler(Looper.getMainLooper()).post {
                buildElements(downloadResultPost.data)
            }
        } else {
            println(downloadResultPost.exception)
            //err
        }
    }

    private fun _LinearLayout.buildElements(post: Post) {
        val postDataConverter = PostDataConverter(post)
        ViewHolderUiInfos(postDataConverter).createView(AnkoContext.createDelegate(this))
    }

    private fun _LinearLayout.addPostTags() = viewModel.launch {
        val downloadResultPost = viewModel.getPost().await()
        if (downloadResultPost.data != null) {
            Handler(Looper.getMainLooper()).post {
                chipGroup {
                    setTags(downloadResultPost.data.tags)
                    setPadding(dip(8), dip(10), dip(8), 0)
                }.lparams {
                    setMargins(0, 0, 0, dip(8))
                }
            }
        } else {
            println(downloadResultPost.exception)
            //err
        }
    }

    private fun ChipGroup.setTags(tags: Array<Tag>) {
        for (tag in tags) {
            if (tag.name.isBlank()) continue
            Handler(Looper.getMainLooper()).post {
                addChip {
                    text = tag.name
                    onClick { }
                }
            }
        }
    }

    private fun ChipGroup.addChip(action: Chip.() -> Unit) {
        addView(Chip(context).apply(action))
    }
}