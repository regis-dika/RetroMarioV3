package com.example.retromariokmm.android.ui.lifeanddifficulty

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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

    val life = remember() {
        mutableStateOf("0")
    }
    val difficulty = remember() {
        mutableStateOf("0")
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        BasicTextField(
            value = life.value,
            onValueChange = {
                life.value = it
                if (it.isNotEmpty()) {
                    lifeAndDifficultyViewModel.onLifeChange(it.toInt())
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan)
        )
        BasicTextField(
            value = difficulty.value,
            onValueChange = {
                difficulty.value = it
                if (it.isNotEmpty()) {
                    lifeAndDifficultyViewModel.onDifficultyChange(it.toInt())
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan)
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(onClick = { lifeAndDifficultyViewModel.setLifeAndDifficulty() }) {
                Text(text = "Valider")
            }
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
                        RetroUserItem(
                            userContainer = user,
                            backgroundColor = if (user.isCurrentUser) Color.Red else Color.Cyan,
                            onUserClick = { },
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