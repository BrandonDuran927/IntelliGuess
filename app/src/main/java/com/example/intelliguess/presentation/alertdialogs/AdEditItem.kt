package com.example.intelliguess.presentation.alertdialogs

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelliguess.IntelliGuessViewModel
import com.example.intelliguess.R
import com.example.intelliguess.data.SubjCollectionEnt

@Composable
fun IntelliGuessEditItem(
    obj: SubjCollectionEnt,
    isEditing: MutableState<Boolean>,
    editedDesc: MutableState<String>,
    currTitle: MutableState<String>,
    viewModel: IntelliGuessViewModel
) {
    val loc = LocalContext.current  // Get the current context
    if (isEditing.value) {
        AlertDialog(
            onDismissRequest = { isEditing.value = false }, // Dispose the dialog
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${currTitle.value}",
                        fontSize = 24.sp,
                        color = colorResource(id = R.color.Secondary),
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedTextField(
                            value = editedDesc.value,
                            onValueChange = { editedDesc.value = it },
                            label = { Text(text = "Edit description", color = Color.Black) },
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
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            editedDesc.value = ""
                        },
                        colors = ButtonDefaults.buttonColors(
                            colorResource(id = R.color.white).copy(alpha = 0.5F)
                        )
                    ) {
                        Text(
                            text = "Reset",
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            if (currTitle.value.isNotEmpty() && editedDesc.value.isNotEmpty()) {
                                viewModel.modifyMap(obj, currTitle.value, editedDesc.value)
                                isEditing.value = false
                                obj.isEditing = false
                                //viewModel.setFalseSubj(obj)
                            } else {
                                Toast.makeText(loc, "Description must not be empty", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            colorResource(id = R.color.Secondary)
                        )
                    ) {
                        Text(
                            text = "Done",
                            color = Color.White
                        )
                    }
                }
            },
            containerColor = Color.White
        )
    }
}
