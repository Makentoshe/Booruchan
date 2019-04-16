package com.makentoshe.booruchan.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.component.tag.Tag
import org.jetbrains.anko.custom.ankoView

open class _ChipGroup(ctx: Context) : ChipGroup(ctx) {

    inline fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?,
        init: ChipGroup.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ChipGroup.LayoutParams(c!!, attrs!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T : View> T.lparams(
        c: Context?,
        attrs: AttributeSet?
    ): T {
        val layoutParams = ChipGroup.LayoutParams(c!!, attrs!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        init: ChipGroup.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ChipGroup.LayoutParams(width, height)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T : View> T.lparams(
        width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = ChipGroup.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.LayoutParams?,
        init: ChipGroup.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T : View> T.lparams(
        source: ViewGroup.LayoutParams?
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?,
        init: ChipGroup.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T : View> T.lparams(
        source: ViewGroup.MarginLayoutParams?
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

    inline fun <T : View> T.lparams(
        source: ChipGroup.LayoutParams?,
        init: ChipGroup.LayoutParams.() -> Unit
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        layoutParams.init()
        this@lparams.layoutParams = layoutParams
        return this
    }

    fun <T : View> T.lparams(
        source: ChipGroup.LayoutParams?
    ): T {
        val layoutParams = ChipGroup.LayoutParams(source!!)
        this@lparams.layoutParams = layoutParams
        return this
    }

}

fun ViewManager.chipGroup(init: _ChipGroup.() -> Unit) = ankoView({ _ChipGroup(it) }, 0, init)

fun ChipGroup.addTagToChipGroup(tag: Tag): Chip {
    return Chip(ContextThemeWrapper(context.applicationContext, R.style.AppBaseChip)).apply {
        text = tag.title
    }.also { addView(it) }
}