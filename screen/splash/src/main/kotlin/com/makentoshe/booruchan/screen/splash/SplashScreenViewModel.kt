package com.makentoshe.booruchan.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.usecase.AddBooruContextUseCase
import com.makentoshe.booruchan.feature.boorulist.domain.usecase.GetBooruContextAssetsUseCase
import com.makentoshe.booruchan.library.feature.CoroutineDelegate
import com.makentoshe.booruchan.library.feature.DefaultCoroutineDelegate
import com.makentoshe.booruchan.library.feature.DefaultEventDelegate
import com.makentoshe.booruchan.library.feature.DefaultStateDelegate
import com.makentoshe.booruchan.library.feature.EventDelegate
import com.makentoshe.booruchan.library.feature.StateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val getBooruContextAssets: GetBooruContextAssetsUseCase,
    private val addBooruContext: AddBooruContextUseCase,
) : ViewModel(),
    CoroutineDelegate by DefaultCoroutineDelegate(),
    StateDelegate<SplashState> by DefaultStateDelegate(SplashState.Loading),
    EventDelegate<SplashEvent> by DefaultEventDelegate() {

    init {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler {
            println(it) // TODO: handle error - could not find assets
        }) {
            getBooruContextAssets().collect(::onGetApplicationAssets)
        }
    }

    fun handleEvent(event: SplashEvent) {
        println(event)
    }

    private fun onGetApplicationAssets(booruContextList: List<BooruContext>) {
        viewModelScope.launch(Dispatchers.IO) {
            // Add booru context to datastore separately and await all jobs
            booruContextList.map { booruContext ->
                viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler {
                    println(it) // TODO: handle error - asset as already added to the datastore
                }) {
                    addBooruContext(booruContext)
                    println("$booruContext finished")
                }
            }.forEach { job -> job.join() }

            // ready to showing a content
            updateState { return@updateState SplashState.Content }
        }
    }
}
