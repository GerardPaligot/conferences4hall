package org.gdglille.devfest.android.theme.m3.networking.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import org.gdglille.devfest.models.ui.Field
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileInputField(
    label: StringResource,
    value: String,
    field: Field,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    onDone: (KeyboardActionScope.() -> Unit)? = null,
    onValueChanged: (Field, String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { text -> onValueChanged(field, text) },
        label = { Text(text = stringResource(label)) },
        keyboardActions = KeyboardActions(onDone = onDone),
        keyboardOptions = KeyboardOptions(
            imeAction = if (onDone != null) ImeAction.Done else ImeAction.Next,
            keyboardType = keyboardType
        ),
        singleLine = true,
        maxLines = 1,
        modifier = modifier.fillMaxWidth()
    )
}
