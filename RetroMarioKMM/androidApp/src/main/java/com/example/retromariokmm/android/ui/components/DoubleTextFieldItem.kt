package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DoubleTextFieldItem(
    firstEdiTitleValue: Pair<String, String>,
    onFirstEdtChange: ((String) -> Unit),
    secondEdiTitleValue: Pair<String, String>,
    onSecondEdtChange: ((String) -> Unit),
    validButton: Pair<String, (() -> Unit)>,
    isEnable: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = firstEdiTitleValue.second,
            placeholder = { Text(text = firstEdiTitleValue.first) },
            enabled = isEnable,
            onValueChange = {
                onFirstEdtChange.invoke(it)
            })
        OutlinedTextField(
            value = secondEdiTitleValue.second,
            placeholder = { Text(text = secondEdiTitleValue.first) },
            enabled = isEnable,
            onValueChange = {
                onSecondEdtChange.invoke(it)
            })
        OutlinedButton(onClick = { validButton.second.invoke() }) {
            Text(text = validButton.first)
        }
    }
}