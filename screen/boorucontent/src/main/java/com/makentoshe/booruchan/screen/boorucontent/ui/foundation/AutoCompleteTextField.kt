package com.makentoshe.booruchan.screen.boorucontent.ui.foundation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@Composable
fun AutoCompleteTextField(
    value: String,
    setValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    dropDownExpanded: Boolean,
    list: List<String>,
    label: (@Composable () -> Unit)? = null,
    supportingText: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    shape: Shape = TextFieldDefaults.shape,
    trailingIcon: (@Composable () -> Unit)? = null,
) = Box(modifier = modifier) {
    TextField(
        modifier = Modifier.fillMaxWidth().onFocusChanged { focusState ->
            if (!focusState.isFocused) onDismissRequest()
        },
        trailingIcon = trailingIcon,
        value = value,
        label = label,
        onValueChange = setValue,
        keyboardOptions = keyboardOptions,
        shape = shape,
        supportingText = supportingText,
    )

    DropdownMenu(
        modifier = Modifier.height(324.dp).fillMaxWidth(),
        expanded = dropDownExpanded,
        properties = PopupProperties(
            focusable = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
        onDismissRequest = onDismissRequest,
    ) {
        list.forEach { text ->
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                text = { Text(text = text) },
                onClick = { setValue(text) },
            )
        }
    }
}
