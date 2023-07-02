package com.makentoshe.screen.boorulist.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makentoshe.library.uikit.theme.BooruchanTheme
import com.makentoshe.screen.boorulist.viewmodel.BoorulistEvent
import com.makentoshe.screen.boorulist.viewmodel.BoorulistStateContent

@Composable
internal fun BoorulistScreenUiContent(
    state: BoorulistStateContent.Content,
    event: (BoorulistEvent) -> Unit,
) = LazyColumn {
    itemsIndexed(state.booruItems) { index, booruItemState ->
        BoorulistItem(index = index, booruItemState = booruItemState, event = event)

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = BooruchanTheme.colors.separator,
            thickness = 1.dp,
        )
    }
}
