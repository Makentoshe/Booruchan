package com.makentoshe.screen.boorulist.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makentoshe.library.uikit.theme.BooruchanTheme
import com.makentoshe.screen.boorulist.entity.SourceUiState
import com.makentoshe.screen.boorulist.viewmodel.HomeScreenEvent
import com.makentoshe.screen.boorulist.viewmodel.HomeScreenPluginContent
import com.makentoshe.screen.boorulist.viewmodel.HomeScreenState

@Composable
internal fun HomeScreenUiContent(
    state: HomeScreenState,
    event: (HomeScreenEvent) -> Unit,
    pluginContent: HomeScreenPluginContent.Content,
) = LazyColumn {
    itemsIndexed(pluginContent.sources) { index, source ->
        SourceItem(sourceUiState = source) {

        }

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = BooruchanTheme.colors.separator,
            thickness = 1.dp,
        )
    }
}
