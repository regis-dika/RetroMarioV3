package com.example.retromariokmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.retromariokmm.android.ui.components.NoteItem
import com.example.retromariokmm.android.ui.lifeanddifficulty.LifeAndDifficultyViewModel
import com.example.retromariokmm.android.ui.login.LoginScreen
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFFBB86FC),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    } else {
        lightColors(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    }
    val typography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login_screen") {
                        composable("login_screen") {
                            LoginScreen(navController)
                        }
                        composable("life_difficulty_screen") {
                            Greeting()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Greeting(lifeAndDifficultyViewModel: LifeAndDifficultyViewModel = hiltViewModel()) {
    val state = lifeAndDifficultyViewModel.usersState.collectAsState()

    val life = remember(){
        mutableStateOf("0")
    }
    val difficulty = remember(){
        mutableStateOf("0")
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(4.dp)) {
        BasicTextField(
            value = life.value,
            onValueChange = {
                life.value = it
                if(it.isNotEmpty()) {
                    lifeAndDifficultyViewModel.onLifeChange(it.toInt())
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Companion.Cyan)
        )
        BasicTextField(
            value = difficulty.value,
            onValueChange = {
                difficulty.value = it
                if(it.isNotEmpty()) {
                    lifeAndDifficultyViewModel.onDifficultyChange(it.toInt())
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan)
        )
        OutlinedButton(onClick = {lifeAndDifficultyViewModel.setLifeAndDifficulty()}) {
            Text(text = "Valider")
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)) {
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
                        NoteItem(
                            userContainer = user,
                            backgroundColor = if(user.isCurrentUser) Color.Red else Color.Cyan  ,
                            onNoteClick = { },
                            onDeleteClick = { },
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

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
    }
}
