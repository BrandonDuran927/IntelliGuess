package com.example.intelliguess.presentation.alertdialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.intelliguess.R

@Composable
fun RevealAnswer(
    isRevealAnswer: MutableState<Boolean>,
    answer: String
) {
    if (isRevealAnswer.value) {
        AlertDialog(
            onDismissRequest = {
                isRevealAnswer.value = false
            },
            confirmButton = {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = answer,
                        color = colorResource(R.color.Secondary),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )
                }
            },
            containerColor = Color.White
        )
    }
}