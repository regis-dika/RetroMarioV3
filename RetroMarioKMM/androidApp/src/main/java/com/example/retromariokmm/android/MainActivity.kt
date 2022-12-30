package com.example.retromariokmm.android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.retromariokmm.android.ui.actions.details.ActionDetailsScreen
import com.example.retromariokmm.android.ui.actions.list.ActionsScreen
import com.example.retromariokmm.android.ui.comments.board.CommentsBoardScreen
import com.example.retromariokmm.android.ui.comments.details.CommentDetailsScreen
import com.example.retromariokmm.android.ui.comments.list.CommentsScreen
import com.example.retromariokmm.android.ui.lifeanddifficulty.UserHealthScreen
import com.example.retromariokmm.android.ui.login.LoginScreen
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

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainActivityViewModel = hiltViewModel()
            val state = viewModel.userState.collectAsState()

            MyApplicationTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Salut " + state.value.name)
                            },
                            navigationIcon = {
                                IconButton(modifier = Modifier.clip(CircleShape), onClick = {}) {
                                    AsyncImage(model = state.value.bitmap, contentDescription = null)
                                }
                            },
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White,
                            elevation = 10.dp
                        )
                    }, content = {
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = "login_screen") {
                            composable("login_screen") {
                                LoginScreen(navController)
                            }
                            composable("life_difficulty_screen") {
                                UserHealthScreen(navController)
                            }
                            composable("comments_board_screen") {
                                CommentsBoardScreen(navController = navController)
                            }
                            composable("comments_screen/{path}", arguments = listOf(
                                navArgument(name = "path") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )) { backStackEntry ->
                                val path = backStackEntry.arguments?.getString("path") ?: ""
                                CommentsScreen(path, navController = navController)
                            }
                            composable("comment_details_screen/{commentId}/{path}", arguments = listOf(
                                navArgument(name = "commentId") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(name = "path") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )) { backStackEntry ->
                                val commentId = backStackEntry.arguments?.getString("commentId") ?: ""
                                val path = backStackEntry.arguments?.getString("path") ?: ""
                                CommentDetailsScreen(path, commentId, navController)
                            }
                            composable("actions_screen") {
                                ActionsScreen(navController = navController)
                            }
                            composable("action_details_screen/{actionId}", arguments = listOf(
                                navArgument(name = "actionId") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )) { backStackEntry ->
                                val actionId = backStackEntry.arguments?.getString("actionId") ?: ""
                                ActionDetailsScreen(actionId, navController)
                            }
                        }
                    })
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
