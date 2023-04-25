package com.example.retromariokmm.android.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.retromariokmm.android.helper.RetroBorder
import com.example.retromariokmm.android.ui.retros.list.RetroContainer
import com.example.retromariokmm.domain.models.UserAction

@Composable
fun RetroItem(
    retroContainer: RetroContainer,
    onCardClick: (String) -> Unit,
    onResumeClick: (String) -> Unit,
) {
    val isExpanded = rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(isExpanded.value) {
        if (isExpanded.value) {
            onCardClick.invoke(retroContainer.retroId)
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .RetroBorder()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            CardHeaderWithExpandedState(title = retroContainer.title, isExpanded = isExpanded.value) {
                isExpanded.value = !isExpanded.value
            }
            AnimatedVisibility(visible = isExpanded.value) {
                RetroCardExpanded(
                    list = retroContainer.actionsList,
                    onResumeClick = { onResumeClick.invoke(retroContainer.retroId) })
            }
        }
    }
}

@Composable
fun CardHeaderWithExpandedState(
    title: String,
    isExpanded: Boolean,
    onCardClick: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCardClick.invoke(!isExpanded)
            }, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "icon retro"
            )
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = title)
                Text(text = "14/02/2023")
            }
        }
        AnimatedVisibility(visible = isExpanded) {
            Box() {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "delete retro")
            }
        }
    }
}

@Composable
fun RetroCardExpanded(list: List<UserAction>, onResumeClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceAround) {
        if (list.isEmpty()) {
            Text(text = "No action taken for this retro")
        } else {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(list) { action ->
                    ActionCheckable(
                        actionTitle = action.title,
                        actionDescription = action.description,
                        isCheck = action.isCheck,
                        onCheckClick = {})
                }
            }
        }
        Box(contentAlignment = Alignment.CenterEnd) {
            OutlinedButton(onClick = { onResumeClick.invoke() }) {
                Text(text = "Reprendre")
            }
        }
    }
}