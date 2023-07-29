package com.makentoshe.screen.boorulist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makentoshe.library.uikit.theme.BooruchanTheme
import com.makentoshe.screen.boorulist.viewmodel.HomeScreenEvent
import com.makentoshe.screen.boorulist.viewmodel.HomeScreenPluginContent
import com.makentoshe.screen.boorulist.viewmodel.HomeScreenState

@Composable
@OptIn(ExperimentalMaterialApi::class)
internal fun HomeScreenUiContent(
    screenState: HomeScreenState,
    screenEvent: (HomeScreenEvent) -> Unit,
    pluginContent: HomeScreenPluginContent.Content,
) {
    var refreshing by remember(key1 = pluginContent) { mutableStateOf(pluginContent.refreshing) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            refreshing = true
            screenEvent(HomeScreenEvent.RefreshPlugins)
        },
    )

    Box(
        modifier = Modifier.fillMaxSize().pullRefresh(pullRefreshState),
        contentAlignment = Alignment.TopCenter,
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(pluginContent.sources) { source ->
                SourceItem(
                    sourceUiState = source,
                    onClick = { screenEvent(HomeScreenEvent.NavigationSource(sourceId = source.id)) },
                )

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = BooruchanTheme.colors.separator,
                    thickness = 1.dp,
                )
            }
        }

        PullRefreshIndicator(refreshing = refreshing, state = pullRefreshState)
    }
}
