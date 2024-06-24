package com.example.intelliguess.presentation.alertdialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelliguess.R

@Composable
fun UserHint(
    hint: MutableState<Boolean>,
    entry: MutableMap.MutableEntry<String, String>?
) {
    if (hint.value) {
        AlertDialog(
            onDismissRequest = { hint.value = false }, // Dispose the dialog
            text = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "HINT",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.Secondary)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Image(
                            painter = painterResource(id = R.drawable.bulb),
                            contentDescription = "Hint Icon",
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        // Makes the key become a list
                        val splitKey = entry?.key?.split(' ')
                        // Greater than 1, will display the total size of splitKey
                        if (splitKey?.size!! > 1) {
                            Text(
                                text = "The answer consists of ${splitKey.size} words",
                                fontSize = 20.sp
                            )
                        // Display only the char length of the word
                        } else {
                            Text(
                                text = "This word has ${entry.key.length} letters",
                                fontSize = 20.sp
                            )
                        }

                    }
                }
            },
            confirmButton = { /* Does not do anything */ }
        )
    }
}