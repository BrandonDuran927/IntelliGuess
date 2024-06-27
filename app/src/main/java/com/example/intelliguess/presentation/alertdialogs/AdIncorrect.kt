package com.example.intelliguess.presentation.alertdialogs

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun IncorrectAnswer(
    isWrong: MutableState<Boolean>
) {
    if (isWrong.value) {
        AlertDialog(
            onDismissRequest = { isWrong.value = false },
            confirmButton = {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Incorrect Answer, Please Try Again",
                        color = Color.Red,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )
                }
            }
        )
    }
}