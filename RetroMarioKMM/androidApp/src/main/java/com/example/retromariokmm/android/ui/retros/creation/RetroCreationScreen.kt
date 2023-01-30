package com.example.retromariokmm.android.ui.retros.creation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.retromariokmm.utils.Error
import com.example.retromariokmm.utils.Loading
import com.example.retromariokmm.utils.Success
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.dynamicLink

@Composable
fun RetroCreationScreen(
    navController: NavController,
    viewModel: RetroCreationViewModel = hiltViewModel()
) {
    val state = viewModel.retroCreationState.collectAsState()
    val clipboardManager = LocalClipboardManager.current
    when (val res = state.value) {
        is Error -> Snackbar() {
            Text(text = res.msg)
        }
        is Loading -> CircularProgressIndicator()
        is Success -> {
            Column(Modifier.fillMaxSize()) {
                state.value.value?.urlToShare?.let {
                    Text(modifier = Modifier.clickable {
                        clipboardManager.setText(AnnotatedString("https://retromariokmm.page.link/?link=https://www.example.com/$it&apn=com.example.retromariokmm.android"))
                    }, text = "https://retromariokmm.page.link/?link=https://www.example.com/$it&apn=com.example.retromariokmm.android") }
            }
        }
    }
}