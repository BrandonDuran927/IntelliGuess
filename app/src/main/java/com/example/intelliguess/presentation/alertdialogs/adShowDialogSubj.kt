package com.example.intelliguess.presentation.alertdialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelliguess.IntelliGuessViewModel
import com.example.intelliguess.R

@Composable
fun ShowDialogSubj(
    showDialogSubj: MutableState<Boolean>,
    addedSubj: MutableState<String>,
    viewModel: IntelliGuessViewModel
) {
    if (showDialogSubj.value) {
        AlertDialog(
            onDismissRequest = { showDialogSubj.value = false }, // Dispose the dialog
            text = {
                Column {
                    Text(
                        text = "Add Category",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.Secondary)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = addedSubj.value,
                        onValueChange = { addedSubj.value = it },
                        label = { Text(text = "Add your subject", color = Color.Black) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = Color.Black,
                            focusedBorderColor = Color.Black
                        )
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    // Checks whether the addSubj is not empty
                    if (addedSubj.value.isNotEmpty()) {
                        viewModel.add(addedSubj.value.uppercase())
                        showDialogSubj.value = false
                        addedSubj.value = ""
                    }
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.Secondary)
                    )
                ) {
                    Text(
                        text = "Add",
                        color = Color.White
                    )
                }
            },
            containerColor = Color.White
        )
    }

}