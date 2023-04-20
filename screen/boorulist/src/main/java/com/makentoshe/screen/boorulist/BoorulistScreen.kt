package com.makentoshe.screen.boorulist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.makentoshe.booruchan.library.navigation.BoorulistScreenNavigator
import com.makentoshe.library.uikit.foundation.TitleText
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
fun BoorulistScreen(
    navigator: BoorulistScreenNavigator,
) = Scaffold(
    topBar = { BoorulistTopBar() },
    content = { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            BoorulistScreenContent(navigator = navigator)
        }
    }
)

@Composable
private fun BoorulistTopBar() = Column(modifier = Modifier.fillMaxWidth()) {
    Box(
        modifier = Modifier.fillMaxWidth().height(56.dp).padding(start = 16.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        TitleText(text = "Booruchan")
    }

    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = BooruchanTheme.colors.separator,
        thickness = 1.dp,
    )
}

@Composable
private fun BoorulistScreenContent(
    navigator: BoorulistScreenNavigator,
) {
    Text("Boorulist")
}