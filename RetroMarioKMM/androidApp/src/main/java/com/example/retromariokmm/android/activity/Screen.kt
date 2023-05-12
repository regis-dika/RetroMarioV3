package com.example.retromariokmm.android.activity

import android.widget.ActionMenuView
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.retromariokmm.android.ui.menu.ActionMenuItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

enum class AppBarIcons {
    Next,
    Add,
    Settings
}

sealed interface Screen {
    val route: String
    val isAppBarVisible: Boolean
    val navigationIcon: ImageVector?
    val navigationIconContentDescription: String?
    val onNavigationIconClick: (() -> Unit)?
    val title: String
    val actions: List<ActionMenuItem>

    object Login : Screen {
        override val route: String = "login_screen"
        override val isAppBarVisible: Boolean = true
        override val navigationIcon: ImageVector? = null
        override val onNavigationIconClick: (() -> Unit)? = null
        override val navigationIconContentDescription: String? = null
        override val title: String = "Login"
        override val actions: List<ActionMenuItem> = listOf()
    }

    object Retros : Screen {
        private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
        val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()

        override val route: String = "retros_screen"
        override val isAppBarVisible: Boolean = true
        override val navigationIcon: ImageVector? = null
        override val onNavigationIconClick: (() -> Unit)? = null
        override val navigationIconContentDescription: String? = null
        override val title: String = "Retros"
        override val actions: List<ActionMenuItem> = listOf(
            ActionMenuItem.IconMenuItem.AlwaysShown(
                title = "Add",
                onClick = {
                    // 3
                    _buttons.tryEmit(AppBarIcons.Add)
                },
                icon = Icons.Filled.Add,
                contentDescription = null,
            )
        )
    }

    object Register : Screen {
        override val route: String = "register_screen/{retroId}"
        override val isAppBarVisible: Boolean = true
        override val navigationIcon: ImageVector? = null
        override val onNavigationIconClick: (() -> Unit)? = null
        override val navigationIconContentDescription: String? = null
        override val title: String = "Register"
        override val actions: List<ActionMenuItem> = listOf()
    }

    object Camera : Screen {
        override val route: String = "camera_preview"
        override val isAppBarVisible: Boolean = true
        override val navigationIcon: ImageVector? = null
        override val onNavigationIconClick: (() -> Unit)? = null
        override val navigationIconContentDescription: String? = null
        override val title: String = "Camera Preview"
        override val actions: List<ActionMenuItem> = listOf()
    }

    object RetroCreation : Screen {
        override val route: String = "retro_creation"
        override val isAppBarVisible: Boolean = true
        override val navigationIcon: ImageVector? = null
        override val onNavigationIconClick: (() -> Unit)? = null
        override val navigationIconContentDescription: String? = null
        override val title: String = "Create Retro"
        override val actions: List<ActionMenuItem> = listOf()
    }

    object Healthy : Screen {

        // 2
        private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
        val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()

        override val route: String = "life_difficulty_screen"
        override val isAppBarVisible: Boolean = true
        override val navigationIcon: ImageVector? = null
        override val onNavigationIconClick: (() -> Unit)? = null
        override val navigationIconContentDescription: String? = null
        override val title: String = "Healthy"
        override val actions: List<ActionMenuItem> = listOf(
            ActionMenuItem.IconMenuItem.AlwaysShown(
                title = "Next",
                onClick = {
                    // 3
                    _buttons.tryEmit(AppBarIcons.Next)
                },
                icon = Icons.Filled.ArrowForward,
                contentDescription = null,
            )
        )
    }

    object CommentBoard : Screen {
        private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
        val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()

        override val route: String = "comments_board_screen"
        override val isAppBarVisible: Boolean = true
        override val navigationIcon: ImageVector? = null
        override val onNavigationIconClick: (() -> Unit)? = null
        override val navigationIconContentDescription: String? = null
        override val title: String = "Comments Board"
        override val actions: List<ActionMenuItem> = listOf(
            ActionMenuItem.IconMenuItem.AlwaysShown(
                title = "Next",
                onClick = {
                    // 3
                    _buttons.tryEmit(AppBarIcons.Next)
                },
                icon = Icons.Filled.ArrowForward,
                contentDescription = null,
            )
        )
    }

    object Comments : Screen {
        override val route: String = "comments_screen/{path}"
        override val isAppBarVisible: Boolean = true
        override val navigationIcon: ImageVector? = null
        override val onNavigationIconClick: (() -> Unit)? = null
        override val navigationIconContentDescription: String? = null
        override val title: String = "Comments"
        override val actions: List<ActionMenuItem> = listOf()
    }

    object Actions : Screen {
        private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
        val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()

        override val route: String = "actions_screen"
        override val isAppBarVisible: Boolean = true
        override val navigationIcon: ImageVector? = null
        override val onNavigationIconClick: (() -> Unit)? = null
        override val navigationIconContentDescription: String? = null
        override val title: String = "Actions"
        override val actions: List<ActionMenuItem> = listOf(
            ActionMenuItem.IconMenuItem.AlwaysShown(
                title = "Next",
                onClick = {
                    // 3
                    _buttons.tryEmit(AppBarIcons.Next)
                },
                icon = Icons.Filled.ArrowForward,
                contentDescription = null,
            ),
            ActionMenuItem.IconMenuItem.AlwaysShown(
                title = "Add",
                onClick = {
                    // 3
                    _buttons.tryEmit(AppBarIcons.Add)
                },
                icon = Icons.Filled.Add,
                contentDescription = null,
            )
        )
    }

    object EditAction : Screen {
        override val route: String = "action_details_screen/{actionId}"
        override val isAppBarVisible: Boolean = true
        override val navigationIcon: ImageVector? = null
        override val onNavigationIconClick: (() -> Unit)? = null
        override val navigationIconContentDescription: String? = null
        override val title: String = "Edit Action"
        override val actions: List<ActionMenuItem> = listOf()
    }
}

fun getScreen(route: String?): Screen? = Screen::class.nestedClasses.map { kClass ->
    kClass.objectInstance as Screen
}.firstOrNull { screen -> screen.route == route }

class AppBarState(
    val navController: NavController,
) {
    // 2
    private val currentScreenRoute: String?
        @Composable get() = navController
            .currentBackStackEntryAsState()
            .value?.destination?.route

    // 3
    val currentScreen: Screen?
        @Composable get() = getScreen(currentScreenRoute)

    // 4
    val isVisible: Boolean
        @Composable get() = currentScreen?.isAppBarVisible == true

    val navigationIcon: ImageVector?
        @Composable get() = currentScreen?.navigationIcon

    val navigationIconContentDescription: String?
        @Composable get() = currentScreen?.navigationIconContentDescription

    val onNavigationIconClick: (() -> Unit)?
        @Composable get() = currentScreen?.onNavigationIconClick

    val title: String
        @Composable get() = currentScreen?.title ?: "null"

    val actions: List<ActionMenuItem>
        @Composable get() = currentScreen?.actions.orEmpty()
}

@Composable
fun rememberAppBarState(
    navController: NavController,
) = remember { AppBarState(navController) }
