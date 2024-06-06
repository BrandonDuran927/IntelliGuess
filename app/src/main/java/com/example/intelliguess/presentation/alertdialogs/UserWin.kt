package com.example.intelliguess.presentation.alertdialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.intelliguess.R

@Composable
fun UserWin(
    userWin: MutableState<Boolean>
) {
    if (userWin.value) {
        AlertDialog(
            onDismissRequest = { userWin.value = false }, 
            text = {
                   Column(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalAlignment = Alignment.CenterHorizontally
                   ) {
                       Text(
                           text = "Well Done! You played it well.",
                           fontSize = 32.sp,
                           fontWeight = FontWeight.Bold,
                           color = colorResource(id = R.color.Secondary),
                           textAlign = TextAlign.Center
                       )
                   }
            },
            confirmButton = { /*TODO*/ }
        )
    }
}