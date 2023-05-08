package com.example.retromariokmm.android

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.example.retromariokmm.android.helper.Permission
import com.example.retromariokmm.android.ui.CameraScreen
import com.example.retromariokmm.android.ui.actions.details.ActionDetailsScreen
import com.example.retromariokmm.android.ui.actions.details.ActionDetailsState
import com.example.retromariokmm.android.ui.actions.details.ActionDetailsViewModel
import com.example.retromariokmm.android.ui.actions.list.ActionsScreen
import com.example.retromariokmm.android.ui.comments.board.CommentsBoardScreen
import com.example.retromariokmm.android.ui.comments.list.CommentListEvent.*
import com.example.retromariokmm.android.ui.comments.list.CommentListScreen
import com.example.retromariokmm.android.ui.comments.list.CommentsScreen
import com.example.retromariokmm.android.ui.comments.list.CommentsViewModel
import com.example.retromariokmm.android.ui.comments.list.NewCommentState
import com.example.retromariokmm.android.ui.components.CameraCapture
import com.example.retromariokmm.android.ui.components.CameraPreview
import com.example.retromariokmm.android.ui.lifeanddifficulty.UserHealthScreen
import com.example.retromariokmm.android.ui.login.LoginScreen
import com.example.retromariokmm.android.ui.login.LoginScreenViewModel
import com.example.retromariokmm.android.ui.register.RegisterScreen
import com.example.retromariokmm.android.ui.register.RegisterViewModel
import com.example.retromariokmm.android.ui.retros.creation.RetroCreationScreen
import com.example.retromariokmm.android.ui.retros.creation.RetroCreationViewModel
import com.example.retromariokmm.android.ui.retros.list.RetroScreen
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
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

    private var incomeDeeplink: Uri? = null
    override fun onStart() {
        super.onStart()
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                // Get deep link from result (may be null if no link is found)
                if (pendingDynamicLinkData != null) {
                    incomeDeeplink = pendingDynamicLinkData.link
                }

                // Handle the deep link. For example, open the linked
                // content, or apply promotional credit to the user's
                // account.
                // ...

            }
            .addOnFailureListener(this) { e -> Log.w(TAG, "getDynamicLink:onFailure", e) }

    }

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
                                state.value.name?.let {
                                    Text(text = "Salut " + state.value.name)
                                }
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
                    }) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login_screen") {
                        composable(route = "login_screen") {
                            val retroId = if (incomeDeeplink == null) null else getLastBitFromUrl(incomeDeeplink!!.path)
                            val loginScreenViewModel: LoginScreenViewModel = hiltViewModel()
                            val credentialsState = loginScreenViewModel.loginCredentials.collectAsState()
                            if (retroId != null) {
                                loginScreenViewModel.onRetroIdAvailable(retroId)
                            }
                            val loginState = loginScreenViewModel.loginState.collectAsState()
                            LoginScreen(
                                retroId,
                                navController,
                                credentialsState = credentialsState.value,
                                loginState = loginState.value,
                                onEmailEvent = { loginScreenViewModel.onEmailChange(it) },
                                onPasswordEvent = { loginScreenViewModel.onPasswordChange(it) },
                                onLoginEvent = {
                                    loginScreenViewModel.onLogin()
                                }
                            )
                        }
                        composable("register_screen/{retroId}", arguments = listOf(
                            navArgument(name = "retroId") {
                                type = NavType.StringType
                                defaultValue = ""
                            }
                        )) { navBackEntry ->
                            val retroId = navBackEntry.arguments?.getString("retroId")
                            RegisterScreen(retroId, navController)
                        }
                        composable("retros_screen") {
                            RetroScreen(navController)
                        }
                        composable("camera_preview") {navBackEntry ->
                            val parentEntry = remember(navBackEntry) {
                                navController.getBackStackEntry("register_screen/{retroId}")
                            }
                            val registerScreenViewModel = hiltViewModel<RegisterViewModel>(parentEntry)
                            CameraScreen(navController, onImageUri = {
                                registerScreenViewModel.onImagePath(it)
                            })
                        }
                        composable("retro_creation") {
                            val retroCreationViewModel: RetroCreationViewModel = hiltViewModel()
                            val retroCreationContainerState = retroCreationViewModel.retroCreationState.collectAsState()
                            RetroCreationScreen(navController, retroCreationContainerState.value, addToRetroEvent = {
                                retroCreationViewModel.addMeToThisRetro()
                            }, onSprintTitleEvent = {
                                retroCreationViewModel.onTitle(it)
                            }, onSprintDescriptionEvent = {
                                retroCreationViewModel.onDescription(it)
                            }, createRetroEvent = {
                                retroCreationViewModel.createRetro()
                            })
                        }
                        composable("life_difficulty_screen") {
                            viewModel.getCurrentUser()
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
                            val commentsViewModel: CommentsViewModel = hiltViewModel()
                            val commentsState = commentsViewModel.commentsState.collectAsState()
                            val newCommentState =
                                commentsViewModel.newCommentState.collectAsState(initial = NewCommentState())
                            val path = backStackEntry.arguments?.getString("path") ?: ""
                            CommentListScreen(
                                path,
                                navController = navController,
                                commentsState.value,
                                newCommentState.value,
                                event = { event ->
                                    when (event) {
                                        CreateCommentEvent -> commentsViewModel.createComment()
                                        is CurrentDescriptionEvent -> commentsViewModel.onCurrentCommentChange(event.description)
                                        is EditCommentEvent -> commentsViewModel.editComment(event.commentId)
                                        is EditDescriptionEvent -> commentsViewModel.onEditDescriptionChange(event.description)
                                        is OnLikeEvent -> commentsViewModel.updateLikeComment(
                                            event.commentId,
                                            event.feeling
                                        )
                                    }
                                }) {
                                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                            }
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
                            val actionDetailsViewModel: ActionDetailsViewModel = hiltViewModel()
                            val actionId = backStackEntry.arguments?.getString("actionId") ?: ""
                            val actionDetailState =
                                actionDetailsViewModel.state.collectAsState(initial = ActionDetailsState())
                            ActionDetailsScreen(actionId, navController, actionDetailState.value, onTitleChange = {
                                actionDetailsViewModel.onChangeTitle(it)
                            }, onDescriptionChange = {
                                actionDetailsViewModel.onChangeDescription(it)
                            }, validateEvent = {
                                actionDetailsViewModel.saveAction()
                            })
                        }
                    }
                }
            }
        }
    }

    private fun getLastBitFromUrl(url: String?): String? {
        // return url.replaceFirst("[^?]*/(.*?)(?:\\?.*)","$1);" <-- incorrect
        return url?.replaceFirst(".*/([^/?]+).*".toRegex(), "$1")
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
    }
}
