package com.example.retromariokmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.NavType.Companion
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.retromariokmm.android.ui.actions.list.ActionsScreen
import com.example.retromariokmm.android.ui.comments.details.CommentDetailsScreen
import com.example.retromariokmm.android.ui.comments.list.StarCommentsScreen
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
                            UserHealthScreen(navController)
                        }
                        composable("comments_screen") {
                            StarCommentsScreen(navController = navController)
                        }
                        composable("comment_details_screen/{commentId}", arguments = listOf(
                            navArgument(name = "commentId"){
                                type = NavType.StringType
                                defaultValue = ""
                            }
                        )) { backStackEntry ->
                            val commentId = backStackEntry.arguments?.getString("commentId") ?: ""
                            CommentDetailsScreen(commentId, navController)
                        }
                        composable("actions_screen") {
                            ActionsScreen(navController = navController)
                        }
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
