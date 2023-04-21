package com.makentoshe.screen.boorulist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.makentoshe.booruchan.library.navigation.BoorulistScreenNavigator
import com.makentoshe.screen.boorulist.BoorulistEvent
import com.makentoshe.screen.boorulist.BoorulistState

@Composable
internal fun BoorulistScreenUiContent(
    state: BoorulistState.Content,
    navigator: BoorulistScreenNavigator,
    viewModelEvent: (BoorulistEvent) -> Unit,
) {
    Column() {
        Text("Boorulist")

        Button(
            onClick = { viewModelEvent(BoorulistEvent.Sas) }
        ) {
            Text("SAS")
        }
    }
}