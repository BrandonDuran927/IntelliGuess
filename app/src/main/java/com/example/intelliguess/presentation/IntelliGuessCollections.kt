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
import com.example.intelliguess.presentation.alertdialogs.IntelliGuessEditItem
import com.example.intelliguess.presentation.alertdialogs.ShowDialogItem
import com.example.intelliguess.presentation.alertdialogs.ShowDialogSubj



@Composable
fun IntelliGuessCollection(
    navController: NavController,
    viewModel: IntelliGuessViewModel
) {
    val expand = remember { mutableStateOf(false) }  // Expand the drop down menu for list of subjects
    val showDialogSubj = remember { mutableStateOf(false) }  // Triggered when the user wants to add a subject
    val showDialogItem = remember { mutableStateOf(false) }  // Triggered when the user wants to add a dictionary
    val isEditing = remember { mutableStateOf(false) }  // Enables the user to edit the dictionary

    val addedSubj = remember { mutableStateOf("") }  // Text of the specific subject
    val title = remember { mutableStateOf("") }  // Title of dictionary
    val description = remember { mutableStateOf("") }  // Description of the dictionary
    val editedDesc = remember { mutableStateOf("") }  // Edited description
    val currTitle = remember { mutableStateOf("") }  // Store the current title

    val loc = LocalContext.current  // Retrieves the current context

    // Observe the list of SubjectCollection
    val collections by viewModel.collections.observeAsState(initial = emptyList())
    // Observe the current selected subject
    val selectedSubj by viewModel.selectedSubj.observeAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.Primary)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                        Toast.makeText(loc, "${collections.size}", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.width(250.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.Secondary)
                    )
                ) {
                    // If collection is not empty, display the text of the selectedSubj subject
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
                    showDialogSubj.value = true // Shows the dialog of adding a subject
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add Subject",
                        tint = colorResource(id = R.color.Secondary),
                        modifier = Modifier.size(40.dp)
                    )
                }
                IconButton(onClick = {
                    // Proceed to home screen
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
                // Display all the subject through dropDownMenuItem composable if collections is not empty
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
                                            // Removes the specific subj
                                            viewModel.remove(subj)
                                            Toast.makeText(loc, "${collections.size}", Toast.LENGTH_SHORT).show()
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
                                // Set the founded subj as current subject
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
                    // Display each item if selectedSubj.subject is equal to obj.subject
                    if (selectedSubj?.subject == obj.subject) {
                        IntelliGuessItem(
                            obj,
                            onDelete = { key ->
                                // Delete the key from the obj (Type of selected subj)
                                viewModel.deleteFromMap(obj, key)
                            },
                            onEdit = { key, value ->
                                isEditing.value = true  // Set the isEditing of obj to true
                                currTitle.value = key  // Set the key as current title
                                editedDesc.value = value  // Set the value as as editedDesc
                                viewModel.startEditing(obj)  // Starts editing the obj
                            }
                        )
                    }
                    // Pop up an alert dialog if obj isEditing
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

            // Shows a plus icon if collections is not empty using FAB
            if (viewModel.collections.value?.isNotEmpty() == true) {
                FloatingActionButton(
                    onClick = {
                        showDialogItem.value = true
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
                    color = Color.White.copy(alpha = 0.4F)  // Adjust the opacity of text
                )
            }
        }

    }
    // Responsible for showing the process of adding a subject if showDialogSubj is true
    ShowDialogSubj(showDialogSubj = showDialogSubj, addedSubj = addedSubj, viewModel = viewModel)
    // If selectedSubj is not null, it will show the process of adding a dictionary
    selectedSubj?.let {
        ShowDialogItem(
            showDialogItem = showDialogItem,
            title = title,
            description = description,
            viewModel = viewModel,
            selectedSubj = it
        )
    }

}


@Preview(showBackground = true)
@Composable
fun IntelliGuessItemPrev() {
    IntelliGuessCollection(
        navController = rememberNavController(),
        viewModel()
    )
}