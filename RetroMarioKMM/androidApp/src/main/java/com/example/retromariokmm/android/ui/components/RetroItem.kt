package com.example.retromariokmm.android.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retromariokmm.android.ui.retros.list.RetroContainer
import com.example.retromariokmm.android.ui.components.cardHeaderWithExpandedState as cardHeaderWithExpandedState1

@Composable
fun NestedScrolling(
    list: List<RetroContainer>,
    modifier: Modifier = Modifier,
    onCardClick: ((String) -> Unit),
    onResumeClick: ((String) -> Unit)
) {
    var isExpanded = rememberSaveable() {
        mutableStateOf(false)
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            list.forEach { retroContainer ->
                RetroItem(retroContainer, onCardClick, onResumeClick, isExpanded.value)
            }
        }
    }
}

fun LazyListScope.RetroItem(
    retroContainer: RetroContainer,
    onCardClick: (String) -> Unit,
    onResumeClick: (String) -> Unit,
    isExpanded: Boolean
) {
    item {
        cardHeaderWithExpandedState1(
            retroContainer.retroId,
            retroContainer.title,
            onCardClick,
            onResumeClick,
            isExpanded
        )
    }
    if (isExpanded) {
        items(retroContainer.actionsList) { action ->
            ActionCheckable(
                actionTitle = action.title,
                actionDescription = action.description,
                isCheck = action.isCheck,
                onCheckClick = {})
        }
    }
    item {
        AnimatedVisibility(visible = isExpanded) {
            Box(contentAlignment = Alignment.CenterEnd) {
                OutlinedButton(onClick = { onResumeClick.invoke(retroContainer.retroId) }) {
                    Text(text = "Reprendre")
                }
            }
        }
    }
}

@Composable
fun cardHeaderWithExpandedState(
    retroId: String,
    title: String,
    onCardClick: (String) -> Unit,
    onResumeClick: (String) -> Unit,
    isExpanded: Boolean
) {
    val isExpanded = rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isExpanded.value = !isExpanded.value
                if (isExpanded.value) {
                    onCardClick.invoke(retroId)
                }
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
        AnimatedVisibility(visible = isExpanded.value) {
            Box() {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "delete retro")
            }
        }
    }
}