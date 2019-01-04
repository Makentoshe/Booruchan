package com.makentoshe.booruchan.posts.view

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import com.makentoshe.booruchan.posts.animations.SearchHideAnimator
import com.makentoshe.booruchan.posts.animations.SearchShowAnimator
import com.makentoshe.booruchan.posts.model.OverflowState
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk27.coroutines.onEditorAction
import org.jetbrains.anko.sdk27.coroutines.textChangedListener

class PostsFragmentUiContentSearch(
    private val postsFragmentViewModel: PostsFragmentViewModel
) : AnkoComponent<RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = wrapContent) { alignParentTop() }
            visibility = View.GONE
            elevation = dip(4).toFloat()
            translationY = -(dip(56).toFloat() + elevation)
            backgroundColorResource = style.toolbar.primaryColorRes
//            setOnClickListener { /* just handle */ }
            setPadding(dip(8), 0, dip(8), 0)
            editTextContainerView(this)
            tagsContainerView()
        }
    }

    private fun _RelativeLayout.editTextContainerView(parent: RelativeLayout) {
        frameLayout {
            id = R.id.search_container_edittext
            setPadding(0, dip(8), 0, dip(8))
            cardView {
                elevation = dip(2).toFloat()
                radius = dip(2).toFloat()
                relativeLayout {
                    autocompleteEditText(parent).progressBar = autocompleteDelayProgressBar()
                    autocompleteDelayClearIcon()
                }
            }.lparams(width = matchParent, height = dip(40))
        }.lparams(width = matchParent, height = dip(56)) {
            alignParentTop()
        }
    }

    private fun _RelativeLayout.autocompleteEditText(parent: RelativeLayout): DelayAutocompleteEditText {
        return delayAutoCompleteEditText {
            setCursorColor(Color.BLACK)
            setAdapter(postsFragmentViewModel.autocompleteAdapter)
            setPadding(0, 0, dip(36), 0)
            singleLine = true
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            addOnSearchActionClickListener()
            addOverflowListener(parent)
            addOnClearClickListener()
            addOnSpaceClickListener()
            addOnTipClickListener()
        }.lparams(matchParent, matchParent)
    }

    private fun DelayAutocompleteEditText.addOnSearchActionClickListener() {
        onEditorAction { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                addTagToListOfSelectedTags(Tag(text.toString()))
                postsFragmentViewModel.startNewSearch()
                postsFragmentViewModel.uiController.action(Action.UIAction.OverflowClick)
            }
        }
    }

    private fun DelayAutocompleteEditText.addOverflowListener(parent: RelativeLayout) {
        postsFragmentViewModel.uiController.addOverflowListener {
            onTransition {
                when (it.finishState) {
                    OverflowState.Magnify -> Handler(Looper.getMainLooper()).postAtFrontOfQueue {
                        SearchHideAnimator(parent, it.transitionDuration).animate()
                        this@addOverflowListener.clearFocus()
                        hideKeyboard(context, this@addOverflowListener)
                    }
                    OverflowState.Cross -> Handler(Looper.getMainLooper()).postAtFrontOfQueue {
                        SearchShowAnimator(parent, it.transitionDuration).animate()
                        this@addOverflowListener.requestFocus()
                        showKeyboard(context, this@addOverflowListener)
                    }
                }
            }
        }
    }

    private fun DelayAutocompleteEditText.addOnClearClickListener() {
        postsFragmentViewModel.uiController.clearIconController.addOnClickListener {
            setText("")
        }
    }

    private fun DelayAutocompleteEditText.addOnSpaceClickListener() {
        textChangedListener {
            afterTextChanged {
                if (text.isNotBlank() && text.last() == ' ') {
                    val tagTitle = text.toString().replace(" ", "")
                    addTagToListOfSelectedTags(Tag(tagTitle))
                }
            }
        }
    }

    private fun DelayAutocompleteEditText.addOnTipClickListener() {
        setOnItemClickListener { parentAdapter, _, position, _ ->
            val tag = parentAdapter.getItemAtPosition(position) as Tag
            addTagToListOfSelectedTags(tag)
        }
    }

    private fun DelayAutocompleteEditText.addTagToListOfSelectedTags(tag: Tag) {
        setText("")
        postsFragmentViewModel.selectedTagSetController.addTag(tag)
    }

    private fun _RelativeLayout.autocompleteDelayProgressBar(): ProgressBar {
        return progressBar {
            visibility = View.INVISIBLE
            isIndeterminate = true
            indeterminateDrawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
        }.lparams(width = dip(36), height = dip(36)) {
            addRule(RelativeLayout.ALIGN_PARENT_END)
            addRule(RelativeLayout.CENTER_VERTICAL)
        }
    }

    private fun _RelativeLayout.autocompleteDelayClearIcon(): ImageView {
        return imageView {
            padding = dip(4)
            setImageResource(style.drawable.static.cross)
            setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
            setOnClickListener(::onClearIconClick)
        }.lparams(width = dip(36), height = dip(36)) {
            addRule(RelativeLayout.ALIGN_PARENT_END)
            addRule(RelativeLayout.CENTER_VERTICAL)
        }
    }

    private fun onClearIconClick(ignored: View) {
        postsFragmentViewModel.uiController.action(Action.UIAction.ClearTextFieldClick)
    }

    private fun _RelativeLayout.tagsContainerView() {
        chipGroup {
            postsFragmentViewModel.selectedTagSetController.subscribeOnAdd {
                createChip(it)
            }
        }.lparams(width = matchParent, height = wrapContent) {
            below(R.id.search_container_edittext)
            setMargins(0, 0, 0, dip(8))
        }
    }

    private fun _ChipGroup.createChip(tag: Tag) {
        chip {
            backgroundDrawable?.setColorFilter(style.chip.getPrimaryColor(context), PorterDuff.Mode.SRC_ATOP)
            textColorResource = style.chip.onPrimaryColorRes
            text = tag.name
            isCloseIconVisible = true
            closeIcon?.setColorFilter(style.chip.getOnPrimaryColor(context), PorterDuff.Mode.SRC_ATOP)
            setOnCloseIconClickListener {
                postsFragmentViewModel.selectedTagSetController.removeTag(tag)
            }
            postsFragmentViewModel.selectedTagSetController.subscribeOnRemove {
                if (tag == it) removeView(this)
                println(postsFragmentViewModel.selectedTagSetController.tags)
            }
        }
    }
}