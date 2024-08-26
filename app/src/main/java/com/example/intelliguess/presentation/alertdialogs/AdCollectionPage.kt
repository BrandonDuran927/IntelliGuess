package com.example.intelliguess.presentation.alertdialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.intelliguess.IntelliGuessViewModel
import com.example.intelliguess.R
import com.example.intelliguess.data.SubjCollectionEnt
import com.example.intelliguess.navigation.Screen

@Composable
fun CollectionPage(
    navController: NavController,
    viewModel: IntelliGuessViewModel,
    isInCollection: MutableState<Boolean>,
    oldSubj: SubjCollectionEnt?
) {
    if (isInCollection.value) {
        AlertDialog(
            onDismissRequest = { isInCollection.value = false }, // Dispose this dialog
            text = {
                Text(
                    color = colorResource(id = R.color.Secondary),
                    text = "Moving to collection page will reset the win streak",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            },
            confirmButton = {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.white)
                        ),
                        onClick = {
                            isInCollection.value = false
                        }
                    ) {
                        Text(
                            color = Color.Black,
                            text = "Dismiss"
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.Secondary)
                        ),
                        onClick = {
                            // Navigate through the item composable screen
                            navController.navigate(route = Screen.Item.route)
                            if (oldSubj != null) {
                                // Retrieve the map odl value if oldSubj is not null
                                viewModel.resetMap(oldSubj)
                            }
                        }
                    ) {
                        Text(
                            color = Color.White,
                            text = "Go to collections"
                        )
                    }
                }
            },
            containerColor = Color.White
        )
    }
}