package com.example.retromariokmm.android.ui

import android.Manifest.permission
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.retromariokmm.android.helper.Permission
import com.example.retromariokmm.android.ui.components.CameraCapture
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CameraScreen(
    navController: NavController,
    onImageUri: ((String?) -> Unit),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val emptyImageUri = Uri.parse("file://dev/null")
    var imageUri by remember { mutableStateOf(emptyImageUri) }
    Permission(
        permission = permission.CAMERA,
        rationale = "You said you wanted a picture, so I'm going to have to ask for permission.",
        permissionNotAvailableContent = {
            Column(Modifier.fillMaxSize()) {
                Text("O noes! No Camera!")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    })
                }) {
                    Text("Open Settings")
                }
            }
        }
    ) {
        if (imageUri != emptyImageUri) {
            Box(modifier = modifier) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = rememberImagePainter(imageUri),
                    contentDescription = "Captured image"
                )
                Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                    Button(
                        onClick = {
                            imageUri = emptyImageUri
                        }
                    ) {
                        Text("Remove image")
                    }
                    Button(
                        onClick = {
                            val encodedUri = imageUri.encodedPath
                            onImageUri.invoke(encodedUri)
                            imageUri = emptyImageUri
                            navController.popBackStack()
                        }
                    ) {
                        Text("Valider")
                    }
                }
            }
        } else {
            CameraCapture(modifier = modifier, onImageFile = { file ->
                imageUri = file.toUri()
            })
        }
    }
}