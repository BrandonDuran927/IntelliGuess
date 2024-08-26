package com.example.intelliguess.presentation.alertdialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelliguess.IntelliGuessViewModel
import com.example.intelliguess.R
import com.example.intelliguess.data.SubjCollectionEnt

@Composable
fun AdDeleteSubject(
    isDeleteSubject: MutableState<Boolean>,
    viewModel: IntelliGuessViewModel,
    subjToDelete: MutableState<SubjCollectionEnt?>
) {
    if (isDeleteSubject.value) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = {
                isDeleteSubject.value = false
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        color = colorResource(id = R.color.Secondary),
                        text = "Do you want to delete this subject?",
                        fontSize = 24.sp,
                        lineHeight = 24.sp
                    )
                }
            },
            confirmButton = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                // Dismiss the alert dialog itself
                                isDeleteSubject.value = false
                            },
                            colors = buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            )
                        ) {
                            Text("No")
                        }
                        Spacer(Modifier.width(16.dp))
                        TextButton(
                            onClick = {
                                // Delete the particular selected subject
                                subjToDelete.value?.let { viewModel.remove(it) }
                                // Dismiss the alert dialog itself
                                isDeleteSubject.value = false
                            },
                            colors = buttonColors(
                                containerColor = colorResource(R.color.Secondary),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Yes")
                        }
                }
            }
        )
    }
}