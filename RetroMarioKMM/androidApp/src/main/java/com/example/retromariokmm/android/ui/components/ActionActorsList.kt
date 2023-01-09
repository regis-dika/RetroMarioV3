package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.retromariokmm.android.R.drawable

@Composable
fun ActionActorsComposable(
    actorsUrl: List<String>,
    modifier: Modifier = Modifier
) {
    LazyRow() {
        items(actorsUrl) { url ->
            AsyncImage(
                model = url,
                contentDescription = "picture",
                error = painterResource(drawable.default_profil_picture),
                modifier = Modifier
                    .size(60.dp)
                    .padding(4.dp)
                    .wrapContentSize()
                    .clip(CircleShape)
            )
        }
    }
}