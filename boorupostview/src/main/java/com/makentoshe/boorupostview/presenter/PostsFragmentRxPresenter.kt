package com.makentoshe.boorupostview.presenter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.jakewharton.rxbinding3.view.clicks
import com.makentoshe.api.DiskCache
import com.makentoshe.api.ImageDiskCache
import com.makentoshe.api.PostDiskCache
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.BuildConfig.DEBUG
import com.makentoshe.boorupostview.PostsFragmentNavigator
import com.makentoshe.boorupostview.R
import com.makentoshe.boorupostview.fragment.PostsContentFragment
import com.makentoshe.boorupostview.fragment.PostsPanelFragment
import com.makentoshe.boorupostview.listener.MagnifyPanelSlideListener
import com.makentoshe.boorupostview.listener.NewSearchStartedListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.find

/**
 * Reactive presenter component for posts screen.
 */
class PostsFragmentRxPresenter(
    override val disposables: CompositeDisposable,
    private val booru: Booru,
    private val tags: Set<Tag>,
    private val fragmentManager: FragmentManager,
    searchStartedListener: NewSearchStartedListener,
    context: Context
) : PostsFragmentPresenter, RxPresenter() {

    /** Observable for on icon click events */
    private val iconViewObservable = PublishSubject.create<Unit>()
    /** Observable for panel slide listener */
    private val slidingPanelObservable = PublishSubject.create<Float>()
    /** Observable for new search event */
    private val searchObservable = PublishSubject.create<Set<Tag>>().also {
        searchStartedListener.onNewSearchStarted(it::onNext)
    }

    init {
        searchObservable.subscribe {
            try {
                if (DEBUG) Log.i(context.getString(R.string.app_name), "Clear all caches")
                PostDiskCache.build(context).clear()
                ImageDiskCache(DiskCache(ImageDiskCache.getPreviewDir(context))).clear()
                if (DEBUG) Log.i(context.getString(R.string.app_name), "Success")
            } catch (e: Exception) {
                if (DEBUG) Log.i(context.getString(R.string.app_name), "Failed: $e")
            }
        }.let(disposables::add)
    }

    override fun bindToolbar(view: Toolbar) {
        view.title = booru.title
        view.subtitle = view.context.getString(R.string.posts)
    }

    override fun bindOptionIcon(view: View) {
        view.clicks().safeSubscribe(iconViewObservable)
        //set magnify icon drawable
        val magnifyDrawable = view.context.getDrawable(com.makentoshe.style.R.drawable.avd_cross_magnify)
        val magnifyIcon = view.find<ImageView>(R.id.magnify_icon)
        magnifyIcon.setImageDrawable(magnifyDrawable)
        //change alpha on slide offset change event
        slidingPanelObservable.subscribe { slideOffset ->
            magnifyIcon.alpha = slideOffset
            magnifyIcon.visibility = if (slideOffset == 0f) View.GONE else View.VISIBLE
        }.let(disposables::add)
    }

    override fun bindSlidingPanel(view: SlidingUpPanelLayout) {
        view.addPanelSlideListener(MagnifyPanelSlideListener(slidingPanelObservable))
        // change panel state on icon view click event
        iconViewObservable.subscribe {
            view.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }.let(disposables::add)
        // attach the panel - posts viewer
        attachFragment(com.makentoshe.boorupostview.R.id.panelview) {
            PostsPanelFragment.build(booru, tags)
        }
        // attach the content - search layout
        attachFragment(com.makentoshe.boorupostview.R.id.contentview) {
            PostsContentFragment.build(booru, tags)
        }
        // change panel state on search started
        searchObservable.subscribe {
            view.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        }.let(disposables::add)
    }

    /** Attaches the fragment attached to the [container] */
    private fun attachFragment(container: Int, factory: () -> Fragment) {
        val f = fragmentManager.findFragmentById(container)
        if (f != null) return else fragmentManager.beginTransaction().add(container, factory()).commit()
    }
}
