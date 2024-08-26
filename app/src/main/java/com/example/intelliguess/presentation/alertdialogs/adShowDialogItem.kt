package com.example.intelliguess.presentation.alertdialogs

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelliguess.IntelliGuessViewModel
import com.example.intelliguess.R
import com.example.intelliguess.data.SubjCollectionEnt
import kotlinx.coroutines.launch

@Composable
fun ShowDialogItem(
    showDialogItem: MutableState<Boolean>,
    title: MutableState<String>,
    description: MutableState<String>,
    viewModel: IntelliGuessViewModel,
    selectedSubj: SubjCollectionEnt,
) {
    val localContext = LocalContext.current

    if (showDialogItem.value) {
        AlertDialog(
            onDismissRequest = { showDialogItem.value = false }, // Dispose the dialog
            text = {
                Column {
                    Text(
                        text = "Add dictionary word for ${selectedSubj?.subject}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.Secondary)

                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = title.value,
                        onValueChange = { title.value = it },
                        label = { Text(text = "Title", color = Color.Black) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = Color.Black,
                            focusedBorderColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description.value,
                        onValueChange = { description.value = it },
                        label = { Text(text = "Description", color = Color.Black) },
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
                TextButton(
                    onClick = {
                        showDialogItem.value = false
                    }
                ) {
                    Text(text = "Dismiss", color = Color.Black)
                }
                Spacer(Modifier.width(16.dp))
                Button(onClick = {
                    if (description.value.length > 140) {
                        Toast.makeText(localContext, "Keep your description under 140 characters", Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }
                    if (title.value.isNotBlank() && description.value.isNotBlank()) {
                        selectedSubj.let { subj ->
                            // Add a pair of dictionary into the map
                            viewModel.modifyMap(subj, title.value.uppercase().trim(), description.value.trim())
                        }
                        title.value = ""
                        description.value = ""
                        showDialogItem.value = false
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