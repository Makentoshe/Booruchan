package com.makentoshe.screen.boorulist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makentoshe.booruchan.library.feature.DefaultEventDelegate
import com.makentoshe.booruchan.library.feature.DefaultStateDelegate
import com.makentoshe.booruchan.library.feature.EventDelegate
import com.makentoshe.booruchan.library.feature.StateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoorulistViewModel @Inject constructor(

) : ViewModel(),
    StateDelegate<BoorulistState> by DefaultStateDelegate(BoorulistState.Loading),
    EventDelegate<BoorulistEvent> by DefaultEventDelegate() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)

            val booruItemStates = (0..6).map {
                listOf(
                    BooruItemState("Gelbooru", "https://gelbooru.com", BooruItemHealthState.Loading),
                    BooruItemState("Danbooru", "https://danbooru.donmai.us", BooruItemHealthState.Ok),
                    BooruItemState("Custombooru", "https://custombooru.sas", BooruItemHealthState.Error),
                )
            }.flatten()

            updateState {
                return@updateState BoorulistState.Content(booruItemStates)
            }
        }
    }

    fun handleEvent(event: BoorulistEvent) {
        println(event)
    }

}