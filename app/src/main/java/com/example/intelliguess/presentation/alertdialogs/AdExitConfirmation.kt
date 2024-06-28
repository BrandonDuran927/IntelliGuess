package com.example.intelliguess.presentation.alertdialogs

import android.app.Activity
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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.intelliguess.IntelliGuessViewModel
import com.example.intelliguess.R
import com.example.intelliguess.data.SubjCollectionEnt


@Composable
fun ExitConfirmation(
    navController: NavController,
    showExitDialog: MutableState<Boolean>,
    viewModel: IntelliGuessViewModel,
    oldSubj: SubjCollectionEnt?
) {
    if (showExitDialog.value) {
        AlertDialog(
            onDismissRequest = { showExitDialog.value = false }, // Dispose the dialog
            text = {
                Column {
                    Text(
                        text = "Are you sure you want to exit?",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
            },
            confirmButton = {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { showExitDialog.value = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.white)
                        )
                    ) {
                        Text(
                            text = "No",
                            color = colorResource(id = R.color.black)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = {
                        viewModel.resetMap(oldSubj!!) // Save the oldMap before the user exit
                        showExitDialog.value = false
                        // Finish the activity
                        (navController.context as Activity).finish()
                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.Secondary)
                        )
                    ) {
                        Text(text = "Yes", color = Color.White)
                    }
                }
            },
            containerColor = Color.White
        )
    }
}