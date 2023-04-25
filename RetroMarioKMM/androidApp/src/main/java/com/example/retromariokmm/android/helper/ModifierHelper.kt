package com.example.retromariokmm.android.helper

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.RetroBorder() = then(
    Modifier.border(
        BorderStroke(4.dp, Color.Gray), shape = RoundedCornerShape(16.dp)
    )
)