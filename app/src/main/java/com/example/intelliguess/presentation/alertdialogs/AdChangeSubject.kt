package com.example.intelliguess.presentation.alertdialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelliguess.IntelliGuessViewModel
import com.example.intelliguess.R
import com.example.intelliguess.data.SubjCollectionEnt

@Composable
fun ChangeSubject(
    changeSubject: MutableState<Boolean>,
    isSubjectChange: MutableState<Boolean>,
    viewModel: IntelliGuessViewModel,
    oldSubj: SubjCollectionEnt,
    foundSubj: SubjCollectionEnt
) {
    if (changeSubject.value) {
        AlertDialog(
            onDismissRequest = { changeSubject.value = false }, // Dispose this dialog
            text = {
                Text(
                    color = colorResource(id = R.color.Secondary),
                    text = "Changing the subject will reset the win streak",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            },
            confirmButton = {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.white)
                        ),
                        onClick = { 
                            changeSubject.value = false
                        }
                    ) {
                        Text(
                            color = Color.Black,
                            text = "Dismiss"
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.Secondary)
                        ),
                        onClick = {
                            // Change the subject and reset the previous subject
                            viewModel.changeSubject(foundSubj, oldSubj)
                            changeSubject.value = false
                            isSubjectChange.value = true
                        }
                    ) {
                        Text(
                            color = Color.White,
                            text = "Change"
                        )
                    }
                }
            },
            containerColor = Color.White
        )
    }
}