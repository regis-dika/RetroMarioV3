package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.retromariokmm.android.activity.AppBarState
import com.example.retromariokmm.android.activity.Screen.Login
import com.example.retromariokmm.android.ui.menu.ActionsMenu

@Composable
fun RetroTopAppBar(
    appBarState: AppBarState,
    picture: String?,
    onLogoutEvent :(() -> Unit),
    modifier: Modifier = Modifier
) {

    var menuExpanded by remember {
        mutableStateOf(false)
    }

    TopAppBar(
        title = {
            Text(text = appBarState.title)
        },
        navigationIcon = {
            IconButton(onClick = {
                appBarState.navController.popBackStack()
            }) {
                Icon(Filled.ArrowBack, "backIcon")
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        elevation = 10.dp,
        actions = {
            if(picture != null) {
                IconButton(modifier = Modifier.clip(CircleShape), onClick = {}) {
                    AsyncImage(model = picture, contentDescription = null)
                }
            }
            val items = appBarState.actions
            if (items.isNotEmpty()) {
                ActionsMenu(
                    items = items,
                    isOpen = menuExpanded,
                    onToggleOverflow = { menuExpanded = !menuExpanded },
                    maxVisibleItems = 3,
                )
            }
            if(appBarState.currentScreen != Login){
                IconButton(onClick = {
                    onLogoutEvent.invoke()
                }) {
                    Icon(Filled.ExitToApp, "logout")
                }
            }
        },
        modifier = modifier
    )
}