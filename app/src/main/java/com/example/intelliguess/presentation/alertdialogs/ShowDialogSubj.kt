package com.example.intelliguess.presentation.alertdialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelliguess.IntelliGuessViewModel

@Composable
fun ShowDialogSubj(
    showDialogSubj: MutableState<Boolean>,
    addedSubj: MutableState<String>,
    viewModel: IntelliGuessViewModel
) {
    if (showDialogSubj.value) {
        AlertDialog(
            onDismissRequest = { showDialogSubj.value = false },
            text = {
                Column {
                    Text(
                        text = "Add Category",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = addedSubj.value,
                        onValueChange = { addedSubj.value = it },
                        label = { Text(text = "Add your subject") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    showDialogSubj.value = false
                    viewModel.add(addedSubj.value.uppercase())
                    addedSubj.value = ""
                }) {
                    Text(text = "Add")
                }
            }
        )
    }

}