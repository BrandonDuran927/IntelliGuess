package com.example.intelliguess.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelliguess.data.SubjCollection

@Composable
fun IntelliGuessItem(
    obj: SubjCollection,
    onDelete: (String) -> Unit,
    onEdit: (String, String) -> Unit
) {
    obj.mapPair.forEach { (key, value) ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .width(200.dp)
            ) {
                Text(
                    text = key,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(text = value)
            }
            IconButton(
                onClick = { onEdit(key, value) }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "Edit Subject",
                )
            }
            IconButton(
                onClick = { onDelete(key) }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Delete Subject",
                )
            }
        }
    }
}
