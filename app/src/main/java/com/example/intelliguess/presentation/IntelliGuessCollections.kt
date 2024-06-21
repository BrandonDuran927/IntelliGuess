package com.example.intelliguess.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.intelliguess.IntelliGuessViewModel
import com.example.intelliguess.R
import com.example.intelliguess.navigation.Screen
import com.example.intelliguess.presentation.alertdialogs.ShowDialogItem
import com.example.intelliguess.presentation.alertdialogs.ShowDialogSubj



@Composable
fun IntelliGuessCollection(
    navController: NavController,
    viewModel: IntelliGuessViewModel
) {
    val expand = remember { mutableStateOf(false) }
    val showDialogSubj = remember { mutableStateOf(false) }
    val showDialogItem = remember { mutableStateOf(false) }
    val isEditing = remember { mutableStateOf(false) }

    val addedSubj = remember { mutableStateOf("") }
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val editedDesc = remember { mutableStateOf("") }
    val currTitle = remember { mutableStateOf("") }

    val loc = LocalContext.current

    val collections by viewModel.collections.observeAsState(initial = emptyList())
    val selectedSubj by viewModel.selectedSubj.observeAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.Primary)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "SelectedSubj: ${selectedSubj?.subject}")
        Text(text = "SelectedSubj editing: ${viewModel.selectedSubj.value?.isEditing}")
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Icon",
            modifier = Modifier
                .size(105.dp)
                .padding(top = 20.dp)
        )
        Box {
            Row {
                Button(
                    onClick = {
                        expand.value = true
                    },
                    modifier = Modifier.width(250.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.Secondary)
                    )
                ) {
                    if (viewModel.collections.value?.isNotEmpty() == true) {
                        selectedSubj?.subject?.let {
                            Text(
                                text = it,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "DropDown",
                        )
                    } else {
                        Text(
                            text = "Add a category",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Arrow")
                    }
                }
                IconButton(onClick = {
                    //shows a dialog
                    showDialogSubj.value = true
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add Subject",
                        tint = colorResource(id = R.color.Secondary),
                        modifier = Modifier.size(40.dp)
                    )
                }
                IconButton(onClick = {
                    navController.navigate(route = Screen.Home.route) {
                        popUpTo(Screen.Home.route)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Done",
                        tint = colorResource(id = R.color.Secondary),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            DropdownMenu(
                expanded = expand.value,
                onDismissRequest = { expand.value = false },
                modifier = Modifier.width(250.dp)
            ) {
                if (collections.isNotEmpty()) {
                    collections.forEach { subj ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = subj.subject,
                                        textAlign = TextAlign.Start,
                                        fontSize = 16.sp,
                                        modifier = Modifier.width(100.dp)
                                    )
                                    Spacer(modifier = Modifier.width(100.dp))
                                    IconButton(
                                        onClick = {
                                            if (viewModel.collections.value?.size == 1) {
                                                expand.value = false
                                            }
                                            viewModel.remove(subj)
                                        },
                                        modifier = Modifier.size(25.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "Remove Object From List"
                                        )
                                    }
                                }
                            },
                            onClick = {
                                // Find the SubjCollection with the matching subject
                                val foundSubj =
                                    viewModel.collections.value?.find { it.subject == subj.subject }!!
                                viewModel.setCurrentSubject(foundSubj)
                                expand.value = false // Dismiss the DropdownMenu
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 40.dp, start = 16.dp, end = 16.dp)
            ) {
                items(collections) { obj ->
                    if (selectedSubj?.subject == obj.subject) {
                        IntelliGuessItem(
                            obj,
                            onDelete = { key ->
                                viewModel.deleteFromMap(obj, key)
                            },
                            onEdit = { key, value ->
                                //obj.isEditing = true
                                //viewModel.setTrueSubj(obj)
                                isEditing.value = true
                                currTitle.value = key
                                editedDesc.value = value
                                viewModel.startEditing(obj)
                            }
                        )
                    }
                    if (obj.isEditing) {
                        IntelliGuessEditItem(
                            obj = obj,
                            isEditing = isEditing,
                            editedDesc = editedDesc,
                            currTitle = currTitle,
                            viewModel = viewModel
                        )
                    }
                }
            }

            if (viewModel.collections.value?.isNotEmpty() == true) {
                FloatingActionButton(
                    onClick = {
                        showDialogItem.value = true
                        Toast.makeText(loc, "${selectedSubj?.subject}", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .padding(40.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Item",
                        tint = colorResource(
                            id = R.color.Secondary
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "© 2024 Brandon Duran",
                    fontStyle = FontStyle.Italic,
                    color = Color.White.copy(alpha = 0.4F)
                )
            }
        }

    }
    ShowDialogSubj(showDialogSubj = showDialogSubj, addedSubj = addedSubj, viewModel = viewModel)
    selectedSubj?.let {
        ShowDialogItem(
            showDialogItem = showDialogItem,
            title = title,
            description = description,
            viewModel = viewModel,
            selectedSubj = it
        )
    }
//    selectedSubj?.let {
//        ShowDialogItem(
//        showDialogItem = showDialogItem,
//        title = title,
//        description = description,
//        viewModel = viewModel,
//        selectedSubj = it
//    )
//    }
}


@Preview(showBackground = true)
@Composable
fun IntelliGuessItemPrev() {
    IntelliGuessCollection(
        navController = rememberNavController(),
        viewModel()
    )
}