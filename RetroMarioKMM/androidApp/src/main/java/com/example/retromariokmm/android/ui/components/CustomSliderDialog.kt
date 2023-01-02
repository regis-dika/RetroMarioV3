package com.example.retromariokmm.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomSliderDialog(value: Int) {

    var sliderPosition by remember { mutableStateOf(value.toFloat()) }

    Text(text = sliderPosition.toString(), style = MaterialTheme.typography.body1)


    Dialog(onDismissRequest = { }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Companion.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Set value",
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
                    RangeSlider(
                        steps = 10,
                        values = 0f..10f,
                        onValueChange = { sliderPosition = it.endInclusive },
                        valueRange = 0f..10f,
                        onValueChangeFinished = {
                            // launch some business logic update with the state you hold
                            // viewModel.updateSelectedSliderValue(sliderPosition)
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