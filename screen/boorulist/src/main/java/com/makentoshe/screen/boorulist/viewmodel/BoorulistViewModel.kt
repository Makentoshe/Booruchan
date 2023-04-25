package com.makentoshe.screen.boorulist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.usecase.GetBooruContextsUseCase
import com.makentoshe.booruchan.healthcheck.domain.HealthcheckUseCase
import com.makentoshe.booruchan.library.feature.CoroutineDelegate
import com.makentoshe.booruchan.library.feature.DefaultCoroutineDelegate
import com.makentoshe.booruchan.library.feature.DefaultEventDelegate
import com.makentoshe.booruchan.library.feature.DefaultNavigationDelegate
import com.makentoshe.booruchan.library.feature.DefaultStateDelegate
import com.makentoshe.booruchan.library.feature.EventDelegate
import com.makentoshe.booruchan.library.feature.NavigationDelegate
import com.makentoshe.booruchan.library.feature.StateDelegate
import com.makentoshe.booruchan.library.logging.internalLogInfo
import com.makentoshe.booruchan.library.logging.internalLogWarn
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
) : ViewModel(), CoroutineDelegate by DefaultCoroutineDelegate(),
    StateDelegate<BoorulistState> by DefaultStateDelegate(BoorulistState.InitialState),
    EventDelegate<BoorulistEvent> by DefaultEventDelegate(),
    NavigationDelegate<BoorulistDestination> by DefaultNavigationDelegate(BoorulistDestination.NoneDestination) {

    init {
        internalLogInfo("OnViewModelConstruct")
        // First of all request all boorus
        viewModelScope.launch(Dispatchers.IO) {
            getBooruContexts().collectLatest(::onGetBooruContexts)
        }
    }

    fun handleEvent(event: BoorulistEvent) = when (event) {
        is BoorulistEvent.NavigateToBoorucontentScreen -> navigateToBoorucontentScreen(event)
    }

    // Note: Can be called multiple times due GetBooruContextsUseCase implementation
    private fun onGetBooruContexts(booruContexts: List<BooruContext>) {
        internalLogInfo("OnGetBooruContexts: $booruContexts")

        // Map BooruContext to BooruItemState
        val booruItemStates = booruContexts.map(booruContext2BooruItemStateMapper::map)

        updateState {
            copy(content = BoorulistStateContent.Content(booruItemStates))
        }

        booruContexts.forEach { booruContext -> healthcheckBooruContext(booruContext) }
    }

    private fun healthcheckBooruContext(booruContext: BooruContext) {
        internalLogInfo("healthcheck invoked: $booruContext")

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler {
            onHealthcheckBooruContextFailure(booruContext, it)
        }) {
            onHealthcheckBooruContextSuccess(booruContext, healthcheck(booruContext))
        }
    }

    private fun onHealthcheckBooruContextFailure(booruContext: BooruContext, throwable: Throwable) {
        internalLogWarn("Healthcheck(${booruContext.title}): $throwable")

        updateBooruItemHealthState(booruContext, BooruItemHealthState.Error)
    }

    private fun onHealthcheckBooruContextSuccess(booruContext: BooruContext, isAvailable: Boolean) {
        internalLogInfo("Healthcheck(${booruContext.title}): $isAvailable")

        if (isAvailable) {
            updateBooruItemHealthState(booruContext, BooruItemHealthState.Ok)
        } else {
            updateBooruItemHealthState(booruContext, BooruItemHealthState.Error)
        }
    }

    private fun updateBooruItemHealthState(
        booruContext: BooruContext,
        healthState: BooruItemHealthState,
    ) = updateState {
        // Check current state is Content
        // We assume that health state updating request will be called
        // right after content will be displayed, so in this case exception will never be thrown
        val content = (content as? BoorulistStateContent.Content) ?: throw IllegalStateException(
            state.toString()
        )

        // O(n^2) but we don't care. This list just cant contain more that 100-200 items
        val booruItems: List<BooruItemState> = content.booruItems.map { state ->
            if (state.url != booruContext.host.url) return@map state else {
                state.copy(health = healthState)
            }
        }

        copy(content = content.copy(booruItems = booruItems))
    }


    private fun navigateToBoorucontentScreen(event: BoorulistEvent.NavigateToBoorucontentScreen) {
        updateNavigation { BoorulistDestination.BoorucontentDestination(event.booruItemState.url) }
    }
}