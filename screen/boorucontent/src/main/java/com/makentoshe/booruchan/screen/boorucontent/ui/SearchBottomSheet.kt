package com.makentoshe.booruchan.screen.boorucontent.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makentoshe.booruchan.screen.boorucontent.domain.SearchTagCategory
import com.makentoshe.booruchan.screen.boorucontent.domain.SearchTagUi
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.AutoCompleteTextField
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.SearchTagChip
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentBottomSheetState
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenEvent
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenState
import com.makentoshe.library.uikit.foundation.IndeterminateProgressBar
import com.makentoshe.library.uikit.foundation.SecondaryText
import com.makentoshe.library.uikit.layout.ChipGroup
import com.makentoshe.library.uikit.theme.BooruchanTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchBottomSheetContent(
    screenState: BoorucontentScreenState,
    screenEvent: (BoorucontentScreenEvent) -> Unit,
    onSearchButtonClicked: () -> Unit,
) = Column(
    modifier = Modifier.fillMaxSize(),
) {
    val coroutineScope = rememberCoroutineScope()

    var autoCompleteTextFieldValue by remember { mutableStateOf("") }
    var autoCompleteOptionsExpanded by remember { mutableStateOf(false) }
    var autoCompleteProgressBarVisible by remember { mutableStateOf(false) }

    // Show dropdown menu and stop progress bar on each new autocomplete result
    LaunchedEffect(key1 = screenState.bottomSheetState.queryAutocomplete) {
        autoCompleteOptionsExpanded = screenState.bottomSheetState.queryAutocomplete.isNotEmpty()
        autoCompleteProgressBarVisible = false
    }

    // Cancel job on new input
    DisposableEffect(key1 = autoCompleteTextFieldValue, effect = {
        val job = coroutineScope.launch {
            if (autoCompleteTextFieldValue.isEmpty()) return@launch
            delay(350) // delay between input and autocomplete starting
            autoCompleteProgressBarVisible = true
            screenEvent(BoorucontentScreenEvent.AutoCompleteTag(autoCompleteTextFieldValue))
        }
        onDispose {
            job.cancel()
            autoCompleteProgressBarVisible = false
        }
    })

    AutoCompleteTextField(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        value = autoCompleteTextFieldValue,
        setValue = { autoCompleteTextFieldValue = it },
        onDismissRequest = { autoCompleteOptionsExpanded = false },
        dropDownExpanded = autoCompleteOptionsExpanded,
        list = screenState.bottomSheetState.queryAutocomplete.map { it.title },
        onDropDownItemClick = { index, tagTitle ->
            screenEvent(BoorucontentScreenEvent.AddSearchTag(tagTitle, index))
            autoCompleteTextFieldValue = ""
            autoCompleteOptionsExpanded = false
        },
        trailingIcon = {
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(imageVector = Icons.Outlined.Close, contentDescription = null)
                if (autoCompleteProgressBarVisible) {
                    IndeterminateProgressBar(modifier = Modifier.size(36.dp))
                }
            }
        }
    )

    Spacer(modifier = Modifier.height(16.dp).fillMaxWidth())

    Divider(modifier = Modifier.fillMaxWidth())

    Spacer(modifier = Modifier.height(16.dp).fillMaxWidth())

    SearchBottomSheetTags(sheetState = screenState.bottomSheetState, screenEvent = screenEvent)

    Button(onClick = { onSearchButtonClicked() }) {
        Text("Search")
    }
}

@Composable
private fun SearchBottomSheetTags(
    sheetState: BoorucontentBottomSheetState,
    screenEvent: (BoorucontentScreenEvent) -> Unit,
) = Column {

    val generalTags = remember(key1 = sheetState.queryTags) {
        sheetState.queryTags.filter { it.category is SearchTagCategory.General }
    }
    if (generalTags.isNotEmpty()) {
        SearchBottomSheetGeneralTags(
            modifier = Modifier.padding(horizontal = 16.dp),
            generalTags = generalTags,
            onCloseChipIconClick = {
                screenEvent(BoorucontentScreenEvent.RemoveSearchTag(it))
            }
        )
    }
}

@Composable
private fun SearchBottomSheetGeneralTags(
    generalTags: List<SearchTagUi>,
    modifier: Modifier = Modifier,
    onCloseChipIconClick: (SearchTagUi) -> Unit,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(8.dp),
) {
    SecondaryText(text = "General", color = BooruchanTheme.colors.foreground)

    ChipGroup(
        modifier = Modifier.fillMaxWidth(),
        spacing = 4.dp
    ) {
        generalTags.forEach { tag ->
            SearchTagChip(modifier = Modifier.height(32.dp), searchTagUi = tag, onCloseIconClick = { onCloseChipIconClick(tag) })
        }
    }
}
