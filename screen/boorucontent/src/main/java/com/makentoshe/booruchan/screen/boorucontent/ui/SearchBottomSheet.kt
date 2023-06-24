package com.makentoshe.booruchan.screen.boorucontent.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.makentoshe.booruchan.screen.boorucontent.viewmodel.BoorucontentScreenState
import com.makentoshe.library.uikit.foundation.SecondaryText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
internal fun SearchBottomSheet(
    screenState: BoorucontentScreenState,
    sheetState: SheetState,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = ModalBottomSheet(
    modifier = Modifier.fillMaxSize().absolutePadding(top = 28.dp),
    sheetState = sheetState,
    onDismissRequest = { coroutineScope.launch { sheetState.hide() } },
) {
    SearchBottomSheetContent(screenState = screenState)
}

@Composable
fun SearchBottomSheetContent(
    screenState: BoorucontentScreenState,
) = Column(
    modifier = Modifier.fillMaxSize(),
) {

    var queryText by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        supportingText = { SecondaryText(text = screenState.bottomSheetState.queryHint) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text,
        ),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        value = queryText,
        onValueChange = { queryText = it }
    )
}

