package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retromariokmm.android.MyApplicationTheme

@Composable
fun ActionCheckable(
    actionTitle: String,
    actionDescription: String,
    isCheck: Boolean,
    onCheckClick: ((Boolean) -> Unit),
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(
                    text = actionTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = actionDescription,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }
            Checkbox(
                checked = isCheck,
                onCheckedChange = {
                    onCheckClick.invoke(it)
                }
            )
        }
    }
}

@Preview
@Composable
fun ActionCheckablePreview() {
    MyApplicationTheme() {
        ActionCheckable(
            actionTitle = "List item",
            actionDescription = "Supporting text",
            isCheck = true,
            onCheckClick = {})
    }
}