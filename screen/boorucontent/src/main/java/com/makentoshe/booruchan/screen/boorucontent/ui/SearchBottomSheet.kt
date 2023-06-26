package com.makentoshe.booruchan.screen.boorucontent.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
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
import com.makentoshe.booruchan.screen.boorucontent.ui.foundation.AutoCompleteTextField
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenEvent
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenState
import com.makentoshe.library.uikit.foundation.IndeterminateProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
internal fun SearchBottomSheet(
    screenState: BoorucontentScreenState,
    screenEvent: (BoorucontentScreenEvent) -> Unit,
    sheetState: SheetState,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = ModalBottomSheet(
    modifier = Modifier.fillMaxSize().absolutePadding(top = 28.dp),
    sheetState = sheetState,
    onDismissRequest = { coroutineScope.launch { sheetState.hide() } },
) {
    SearchBottomSheetContent(screenState = screenState, screenEvent)
}

@Composable
fun SearchBottomSheetContent(
    screenState: BoorucontentScreenState,
    screenEvent: (BoorucontentScreenEvent) -> Unit,
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
            screenEvent(BoorucontentScreenEvent.Autocomplete(autoCompleteTextFieldValue))
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
        list = screenState.bottomSheetState.queryAutocomplete,
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

    Button(onClick = {
        screenEvent(BoorucontentScreenEvent.Search(autoCompleteTextFieldValue))
    }) {
        Text("Search")
    }
}
