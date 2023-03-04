package com.example.retromariokmm.android.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retromariokmm.android.MyApplicationTheme
import com.example.retromariokmm.android.R
import com.example.retromariokmm.domain.models.UserAction
import com.example.retromariokmm.domain.usecases.actions.ActionListFromRetroUseCase

@Composable
fun RetroItem(
    title: String,
    date: String,
    icon: String? = null,
    actions: List<UserAction>,
    onCheckClick: (Boolean) -> Unit,
    onResumeBtnClick: (() -> Unit),
    onCardClick: (() -> Unit),
    modifier: Modifier = Modifier
) {

    var isExpanded = rememberSaveable() {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable {
                isExpanded.value = !(isExpanded.value)
                if (isExpanded.value) {
                    onCardClick.invoke()
                }
            }
    ) {
        LazyColumn(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Row() {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "icon retro"
                        )
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(text = title)
                            Text(text = date)
                        }
                    }
                    AnimatedVisibility(visible = isExpanded.value) {
                        Box() {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "delete retro")
                        }
                    }
                }
            }
            if (isExpanded.value)
            items(actions) { action ->
                ActionCheckable(
                    actionTitle = action.title,
                    actionDescription = action.description,
                    isCheck = action.isCheck,
                    onCheckClick = {
                        onCheckClick.invoke(it)
                    })
            }

        }
    }
}

@Composable
fun ActionListItem(actions: List<UserAction>, onCheckClick: (Boolean) -> Unit) {
    LazyColumn {
        items(actions) { action ->
            ActionCheckable(
                actionTitle = action.title,
                actionDescription = action.description,
                isCheck = action.isCheck,
                onCheckClick = {
                    onCheckClick.invoke(it)
                })
        }
    }
}

@Preview
@Composable
fun RetroItemPreview() {
    MyApplicationTheme() {
        RetroItem(
            title = "Sprint 41",
            date = "14/01/2023",
            actions = listOf(
                UserAction(title = "List item", description = "Supporting text", isCheck = true),
                UserAction(title = "Remerciement", description = "Faire un bisou à Harry", isCheck = false)
            ),
            onCheckClick = {},
            onResumeBtnClick = { }, onCardClick = {})
    }
}