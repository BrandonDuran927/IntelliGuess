package com.example.intelliguess.presentation.alertdialogs

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.intelliguess.IntelliGuessViewModel
import com.example.intelliguess.R
import com.example.intelliguess.data.SubjCollectionEnt
import com.example.intelliguess.navigation.Screen

@Composable
fun IsCollectionEmpty(
    collections: List<SubjCollectionEnt>,
    navController: NavController,
    viewModel: IntelliGuessViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.countDown()
    }

    val timer = viewModel.count.observeAsState(initial =3).value

    // Show CircularProgressIndicator if timer is running and collections are empty
    if (timer > 0 && collections.isEmpty()) {
        Loading()
    }
    // Show message to add category if timer has finished and collections are still empty
    else if (collections.isEmpty()) {
        AlertDialog(
            onDismissRequest = { /* Does not do anything */ },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            navController.navigate(Screen.Item.route)
                        },
                    ) {
                        Text(
                            text = "Add Category First",
                            color = colorResource(id = R.color.Secondary),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Add Collection",
                            tint = colorResource(id = R.color.Secondary),
                            modifier = Modifier.size(50.dp)
                        )
                    }

                }
            },
            containerColor = Color.White
        )
    }
}