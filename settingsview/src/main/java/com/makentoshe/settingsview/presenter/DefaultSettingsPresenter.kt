package com.makentoshe.settingsview.presenter

import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.FragmentManager
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.checkedChanges
import com.makentoshe.settingsview.OnDialogResultListener
import com.makentoshe.settingsview.fragment.SettingsNsfwAlertFragment
import com.makentoshe.settingsview.viewmodel.DefaultSettingsFragmentViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

/**
 * Presenter component performs settings update.
 *
 * @param disposables disposable container.
 * @param viewmodel contains [com.makentoshe.settings.SettingsBuilder] instance used for settings controlling.
 * @param fragmentManager used for a dialog fragment displaying.
 */
class DefaultSettingsPresenter(
    private val disposables: CompositeDisposable,
    private val viewmodel: DefaultSettingsFragmentViewModel,
    private val fragmentManager: FragmentManager
) {
    /** NSFW setting controller */
    private val nsfwSetting = viewmodel.settingsBuilder.buildNsfw()
    /** Controller displays a user agreement when enabled */
    private val nsfwAlertSetting = viewmodel.settingsBuilder.buildNsfwAlert()
    /** Observable for updating a model */
    private val nsfwObservable = PublishSubject.create<Boolean>()
    /** Observable for updating a view*/
    private val nsfwTriggerObservable = PublishSubject.create<Boolean>()

    init {
        // perform nsfw setting update
        nsfwObservable.subscribe(::updateNsfwSetting).let(disposables::add)
    }

    /** Binds a [view] and [trigger] */
    fun bindNsfwSetting(view: View, trigger: CheckBox) {
        trigger.isChecked = nsfwSetting.value
        // change trigger state
        nsfwTriggerObservable.subscribe(trigger::setChecked).let(disposables::add)
        // send inverse check state
        view.clicks().subscribe { nsfwTriggerObservable.onNext(trigger.isChecked.not()) }.let(disposables::add)
        // change state
        trigger.checkedChanges().doOnError { it.printStackTrace() }.safeSubscribe(nsfwObservable)
    }

    /** Performs an alert dialog displaying and nsfw setting controlling */
    private fun updateNsfwSetting(value: Boolean) =
        // just update nsfw setting if should not display an alert message
        if (!value || !nsfwAlertSetting.value) {
            if (!value) nsfwAlertSetting.value = true
            nsfwSetting.value = value
        } else {
            // create dialog with user agreement
            OnDialogResultListener.create().run {
                SettingsNsfwAlertFragment.show(fragmentManager, this)
                // on accept set nsfw setting as true and update trigger
                onPositiveButtonClick = {
                    nsfwSetting.value = true.also(nsfwTriggerObservable::onNext)
                    nsfwTriggerObservable.onNext(true)
                    nsfwAlertSetting.value = false
                }
                // on decline set nsfw setting as false and update trigger
                onNegativeButtonClick = {
                    nsfwSetting.value = false.also(nsfwTriggerObservable::onNext)
                    nsfwTriggerObservable.onNext(false)
                    nsfwAlertSetting.value = true
                }
                // on decline set nsfw setting as false and update trigger
                onCancelListener = {
                    nsfwSetting.value = false.also(nsfwTriggerObservable::onNext)
                    nsfwTriggerObservable.onNext(false)
                    nsfwAlertSetting.value = true
                }
            }
        }

}
