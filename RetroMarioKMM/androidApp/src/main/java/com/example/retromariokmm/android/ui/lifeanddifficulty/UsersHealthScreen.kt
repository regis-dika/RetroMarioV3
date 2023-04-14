package com.example.retromariokmm.android.ui.lifeanddifficulty

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.retromariokmm.android.ui.components.CustomSliderDialog
import com.example.retromariokmm.android.ui.components.HealthyBoardModel.MainUser
import com.example.retromariokmm.android.ui.components.HealthyBoardModel.OtherUser
import com.example.retromariokmm.android.ui.components.RetroUserItem
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserHealthScreen(
    navController: NavController,
    lifeAndDifficultyViewModel: LifeAndDifficultyViewModel = hiltViewModel()
) {
    val state = lifeAndDifficultyViewModel.usersState.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(onClick = { navController.navigate("comments_board_screen") }) {
                Text(text = "Next")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        ) {
            when (val list = state.value.userContainerList) {
                is Error -> Snackbar() {
                    Text(text = list.msg)
                }
                is Loading -> {
                    CircularProgressIndicator()
                }
                is Success -> LazyColumn() {
                    items(list.value, key = {
                        it.uid
                    }) { user ->
                        val l = user.life
                        val d = user.difficulty
                        val userHealth = if (user.isCurrentUser) MainUser(l, d, {
                            lifeAndDifficultyViewModel.setLife(it)
                        }, {
                            lifeAndDifficultyViewModel.setDifficulty(it)
                        }) else OtherUser(l, d)
                        RetroUserItem(
                            userContainer = user,
                            userHealth,
                            onSaveChange = {
                                lifeAndDifficultyViewModel.saveHealth()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                                .animateItemPlacement()
                        )
                    }
                }
            }
        }
    }
}