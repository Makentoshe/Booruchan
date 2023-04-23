package com.makentoshe.screen.boorulist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.usecase.AddBooruContextUseCase
import com.makentoshe.booruchan.feature.boorulist.domain.usecase.GetBooruContextsUseCase
import com.makentoshe.booruchan.healthcheck.domain.HealthcheckUseCase
import com.makentoshe.booruchan.library.feature.CoroutineExceptionHandlerDelegate
import com.makentoshe.booruchan.library.feature.DefaultCoroutineExceptionHandlerDelegate
import com.makentoshe.booruchan.library.feature.DefaultEventDelegate
import com.makentoshe.booruchan.library.feature.DefaultStateDelegate
import com.makentoshe.booruchan.library.feature.EventDelegate
import com.makentoshe.booruchan.library.feature.StateDelegate
import com.makentoshe.screen.boorulist.mapper.BooruContext2BooruItemStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoorulistViewModel @Inject constructor(
    private val getBooruContexts: GetBooruContextsUseCase,
    private val healthcheck: HealthcheckUseCase,
    private val booruContext2BooruItemStateMapper: BooruContext2BooruItemStateMapper,
) : ViewModel(),
    CoroutineExceptionHandlerDelegate by DefaultCoroutineExceptionHandlerDelegate(),
    StateDelegate<BoorulistState> by DefaultStateDelegate(BoorulistState.Loading),
    EventDelegate<BoorulistEvent> by DefaultEventDelegate() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getBooruContexts().collectLatest(::onGetBooruContexts)
        }
    }

    fun handleEvent(event: BoorulistEvent) {
        println(event)
    }

    // Note: Can be called multiple times due GetBooruContextsUseCase implementation
    private fun onGetBooruContexts(booruContexts: List<BooruContext>) {
        println("OnGetBooruContexts")
        // Map BooruContext to BooruItemState
        val booruItemStates = booruContexts.map(booruContext2BooruItemStateMapper::map)

        booruContexts.forEach { booruContext ->
            viewModelScope.launch(Dispatchers.IO + buildCoroutineExceptionHandler {
                println("Healthcheck(${booruContext.title}): $it")
            }) {
                println("Healthcheck(${booruContext.title}): ${healthcheck(booruContext)}")
            }
        }

        updateState {
            return@updateState BoorulistState.Content(booruItemStates)
        }
    }

}