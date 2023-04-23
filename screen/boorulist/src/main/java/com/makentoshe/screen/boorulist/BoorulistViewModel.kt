package com.makentoshe.screen.boorulist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.usecase.AddBooruContextUseCase
import com.makentoshe.booruchan.feature.boorulist.domain.usecase.GetBooruContextsUseCase
import com.makentoshe.booruchan.library.feature.CoroutineExceptionHandlerDelegate
import com.makentoshe.booruchan.library.feature.DefaultCoroutineExceptionHandlerDelegate
import com.makentoshe.booruchan.library.feature.DefaultEventDelegate
import com.makentoshe.booruchan.library.feature.DefaultStateDelegate
import com.makentoshe.booruchan.library.feature.EventDelegate
import com.makentoshe.booruchan.library.feature.StateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoorulistViewModel @Inject constructor(
    private val addBooruContext: AddBooruContextUseCase,
    private val getBooruContexts: GetBooruContextsUseCase
) : ViewModel(),
    CoroutineExceptionHandlerDelegate by DefaultCoroutineExceptionHandlerDelegate(),
    StateDelegate<BoorulistState> by DefaultStateDelegate(BoorulistState.Loading),
    EventDelegate<BoorulistEvent> by DefaultEventDelegate() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getBooruContexts().collectLatest(::onGetBooruContexts)
        }

//        viewModelScope.launch(Dispatchers.IO + buildCoroutineExceptionHandler { throwable ->
//            println(throwable)
//        }) {
//            delay(5000)
//            val booruContext = BooruContext("Gelbooru", BooruSystem.Gelbooru02System, BooruHost("https://gelbooru.com"))
//            addBooruContext(booruContext)
//            println("Add booru context: $booruContext")
//        }
    }

    fun handleEvent(event: BoorulistEvent) {
        println(event)
    }

    private fun onGetBooruContexts(booruContexts: List<BooruContext>) {
        // Map BooruContext to BooruItemState
        val booruItemStates = booruContexts.map { booruContext ->
            BooruItemState(booruContext.title, booruContext.host.url, BooruItemHealthState.Loading)
        }

        updateState {
            return@updateState BoorulistState.Content(booruItemStates)
        }
    }

}