package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomSliderDialog(
    title: String = "Title",
    value: Int,
    onSaveClick: (Int) -> Unit
) {

    val sliderPosition: MutableState<Float> = remember { mutableStateOf(value.toFloat()) }

    Dialog(onDismissRequest = {
    }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Companion.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(color = Companion.Black, text = title, style = MaterialTheme.typography.h1)
                    Text(
                        color = Companion.Black,
                        text = sliderPosition.value.toString(),
                        style = MaterialTheme.typography.body1
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Set value",
                            color = Companion.Black,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Filled.AccountBox,
                            contentDescription = "",
                            tint = colorResource(android.R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Slider(
                        steps = 10,
                        value = sliderPosition.value,
                        onValueChange = {
                            sliderPosition.value = it
                        },
                        valueRange = 0f..10f,
                        onValueChangeFinished = {
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = androidx.compose.ui.graphics.Color.LightGray,
                            activeTrackColor = Companion.Blue
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                onSaveClick.invoke(sliderPosition.value.toInt())
                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "Done")
                        }
                    }
                }
            }
        }
    }
}
