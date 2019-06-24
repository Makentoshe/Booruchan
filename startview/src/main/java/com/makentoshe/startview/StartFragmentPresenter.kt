package com.makentoshe.startview

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupMenu
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.itemClicks
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/** Presenter component for [StartFragment] */
class StartFragmentPresenter(
    override val disposables: CompositeDisposable,
    private val viewModel: StartFragmentViewModel
) : RxPresenter() {

    /** Binds an overflow icon. Should display popup menu on click. */
    fun bindOverflowIcon(view: View) {
        view.clicks().observeOn(Schedulers.io())
            .map { PopupMenu(view.context, view) }
            .doOnNext { it.buildSettingsMenuItem(view.context) }
            .doOnNext { it.setOnMenuItemClickListener(::onClick) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it.show() }.let(disposables::add)
    }

    /** Builds a menu item with settings title */
    private fun PopupMenu.buildSettingsMenuItem(context: Context) {
        val id = com.makentoshe.startview.R.id.settings
        val title = context.getString(R.string.settings)
        menu.add(Menu.NONE, id, 1, title)
    }

    /** Handle on click events for a popup menu */
    private fun onClick(item: MenuItem): Boolean = when (item.itemId) {
        R.id.settings -> true.also { viewModel.navigator.navigateToSettingsScreen() }
        else -> false
    }

    /** Binds a [ListView]. Should contain a list of a booru and navigate to booru screens on click event */
    fun bindListView(view: ListView) {
        // setting for filter by nsfw
        val setting = viewModel.settingsBuilder.buildNsfw().value
        //get list
        val boorus = viewModel.booruRepository.get(Unit)!!.filter { it.isNsfw or setting }
        // add on click listener
        view.itemClicks().map(boorus::get).subscribe(viewModel.navigator::navigateToBooruScreen).let(disposables::add)
        // set adapter
        Single.just(boorus)
            .map { ArrayAdapter(view.context, android.R.layout.simple_list_item_1, it.map { it.title }) }
            .subscribe(view::setAdapter).let(disposables::add)
    }
}
