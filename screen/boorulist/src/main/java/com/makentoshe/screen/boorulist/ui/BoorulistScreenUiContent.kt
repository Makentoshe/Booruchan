package com.makentoshe.screen.boorulist.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makentoshe.booruchan.library.navigation.BoorulistScreenNavigator
import com.makentoshe.library.uikit.theme.BooruchanTheme
import com.makentoshe.screen.boorulist.BoorulistEvent
import com.makentoshe.screen.boorulist.BoorulistState

@Composable
internal fun BoorulistScreenUiContent(
    state: BoorulistState.Content,
    navigator: BoorulistScreenNavigator,
    viewModelEvent: (BoorulistEvent) -> Unit,
) = LazyColumn() {
    items(state.booruItems) { booruItemState ->
        BoorulistItem(booruItemState)

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = BooruchanTheme.colors.separator,
            thickness = 1.dp,
        )
    }
}
