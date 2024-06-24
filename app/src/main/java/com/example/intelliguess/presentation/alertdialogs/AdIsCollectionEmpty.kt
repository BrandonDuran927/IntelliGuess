package com.example.intelliguess.presentation.alertdialogs

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.intelliguess.R
import com.example.intelliguess.data.SubjCollectionEnt
import com.example.intelliguess.navigation.Screen

@Composable
fun IsCollectionEmpty(
    collections: List<SubjCollectionEnt>,
    navController: NavController
) {
    if (collections.isEmpty()) {
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
            }
        )
    }
}