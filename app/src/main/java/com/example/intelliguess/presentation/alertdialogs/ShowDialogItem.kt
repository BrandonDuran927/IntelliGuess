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
import com.example.intelliguess.data.SubjCollectionEnt

@Composable
fun ShowDialogItem(
    showDialogItem: MutableState<Boolean>,
    title: MutableState<String>,
    description: MutableState<String>,
    viewModel: IntelliGuessViewModel,
    selectedSubj: SubjCollectionEnt

) {
    if (showDialogItem.value) {
        AlertDialog(
            onDismissRequest = { showDialogItem.value = false },
            text = {
                Column {
                    Text(
                        text = "Add dictionary word for ${selectedSubj?.subject}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = title.value,
                        onValueChange = { title.value = it },
                        label = { Text(text = "Title") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description.value,
                        onValueChange = { description.value = it },
                        label = { Text(text = "Description") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (title.value.isNotBlank() && description.value.isNotBlank()) {
                        selectedSubj.let { subj ->
                            viewModel.modifyMap(subj, title.value.uppercase().trim(), description.value.trim())
                        }
                        title.value = ""
                        description.value = ""
                        showDialogItem.value = false
                    }
                }) {
                    Text(text = "Add")
                }
            }
        )
    }
}