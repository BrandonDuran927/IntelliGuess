package com.example.intelliguess.presentation.alertdialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.intelliguess.R
import com.example.intelliguess.navigation.Screen

@Composable
fun IsOneDict(
    winStreak: MutableIntState,
    isOnePair: MutableState<Boolean>,
    navController: NavController
) {
    if (isOnePair.value) {
        AlertDialog(
            onDismissRequest = { isOnePair.value = false },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // More than or equal to one display a "One question left" to continue
                    if (winStreak.intValue >= 1) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "One question left",
                            fontSize = 24.sp,
                            color = colorResource(id = R.color.Secondary),
                            textAlign = TextAlign.Center
                        )
                    // The user has one dictionary only
                    } else {
                        Text(
                            text = "You only have one dictionary, add more to skip",
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            color = colorResource(id = R.color.Secondary)
                        )
                    }
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // More than or equal to one display a "One question left" to continue
                    if (winStreak.intValue >= 1) {
                        Button(
                            onClick = {
                                isOnePair.value = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                colorResource(id = R.color.white)
                            )
                        ) {
                            Text(text = "Dismiss", color = Color.Black)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(
                            onClick = {
                                isOnePair.value = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                colorResource(id = R.color.Secondary)
                            )
                        ) {
                            Text(text = "Continue", color = Color.White)
                        }
                    // The user has one dictionary only
                    } else {
                        Button(
                            onClick = {
                                isOnePair.value = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                colorResource(id = R.color.white)
                            )
                        ) {
                            Text(text = "Dismiss", color = Color.Black)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(
                            onClick = {
                                navController.navigate(Screen.Item.route)
                            },
                            colors = ButtonDefaults.buttonColors(
                                colorResource(id = R.color.Secondary)
                            )
                        ) {
                            Text(text = "Add More", color = Color.White)
                        }
                    }
                }
            },
            containerColor = Color.White
        )
    }
}