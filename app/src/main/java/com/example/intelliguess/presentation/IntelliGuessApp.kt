package com.example.intelliguess.presentation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import com.example.intelliguess.data.SubjCollectionEnt
import com.example.intelliguess.navigation.Screen
import com.example.intelliguess.presentation.alertdialogs.ExitConfirmation
import com.example.intelliguess.presentation.alertdialogs.IsCollectionEmpty
import com.example.intelliguess.presentation.alertdialogs.IsOneDict
import com.example.intelliguess.presentation.alertdialogs.UserHint
import com.example.intelliguess.presentation.alertdialogs.UserWin

//TODO: Create a alert dialog instead if they want to exit


@Composable
fun IntelliGuessApp(
    navController: NavController,
    viewModel: IntelliGuessViewModel
) {
    val expand = remember { mutableStateOf(false) }
    val hint = remember { mutableStateOf(false) }
    val isOnePair = remember { mutableStateOf(false) }
    val userWin = remember { mutableStateOf(false) }
    val showExitDialog = remember { mutableStateOf(false) }

    val input = remember { mutableStateOf("") }
    val count = remember { mutableIntStateOf(0) }
    val winStreak = remember { mutableIntStateOf(0) }

    val collections by viewModel.collections.observeAsState(initial = emptyList())
    val selectedSubj by viewModel.selectedSubj.observeAsState()
    var oldSubj by remember { mutableStateOf<SubjCollectionEnt?>(null) }

    LaunchedEffect(selectedSubj) {
        if (selectedSubj != null && oldSubj == null) {
            oldSubj = selectedSubj?.copy()
        }
    }

    val increment = remember { mutableIntStateOf(viewModel.getItemRandomly() ?: 0) }
    val entry = selectedSubj?.let {
        increment.let { it1 ->
            viewModel.getItemRandomly(
                it1.intValue,
                it
            )
        }
    }
    val loc = LocalContext.current

    BackHandler {
        showExitDialog.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.Primary)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Icon",
            modifier = Modifier.size(165.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (selectedSubj?.mapPair?.isNotEmpty() != null && collections.isNotEmpty()) {
                    TextButton(
                        onClick = { expand.value = true },
                        modifier = Modifier.widthIn(100.dp, 200.dp)
                    ) {
                        Text(
                            text = viewModel.selectedSubj.value?.subject ?: "add subject",
                            color = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "DropDown",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = expand.value,
                        onDismissRequest = { expand.value = false },
                        modifier = Modifier.width(200.dp)
                    ) {
                        viewModel.collections.value?.forEach { subj ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = subj.subject.uppercase(),
                                        color = Color.Black
                                    )
                                },
                                onClick = {
                                    val foundSubj = viewModel.collections.value?.find { it.subject == subj.subject }!!
                                    viewModel.setCurrentSubject(foundSubj)
                                    oldSubj = foundSubj
                                    expand.value = false
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    TextButton(onClick = {
                        navController.navigate(route = Screen.Item.route)
                        if (oldSubj != null) {
                            viewModel.resetMap(oldSubj!!)
                        }
                    }) {
                        Text(text = "Collections", color = Color.White, fontSize = 18.sp)
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Collections",
                            tint = Color.White
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(96.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
                .heightIn(100.dp, 250.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectedSubj?.mapPair?.isNotEmpty() == true && viewModel.collections.value?.isNotEmpty() == true) {
                Text(
                    text = entry?.value.toString(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Add dictionary to your ${viewModel.selectedSubj.value?.subject}",
                        fontSize = 32.sp,
                        color = Color.White,
                        lineHeight = 36.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    Button(
                        onClick = {
                            navController.navigate(Screen.Item.route)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.Secondary)
                        )
                    ) {
                        Text(text = "Add Dictionary")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            if (entry != null) {
                TextButton(
                    onClick = {
                        hint.value = true
                    }
                ) {
                    Text(
                        text = "Hint",
                        color = colorResource(id = R.color.Secondary),
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                TextButton(
                    onClick = {
                        if (viewModel.selectedSubj.value?.mapPair?.size == 1) {
                            isOnePair.value = true
                        } else {
                            increment.intValue = (increment.intValue.plus(1)) % oldSubj?.mapPair?.size!!
                        }
                    }
                ) {
                    Text(
                        text = "Skip",
                        color = colorResource(id = R.color.Secondary),
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        if (input.value.lowercase() == entry.key.lowercase()) {
                            count.intValue += 1
                            winStreak.intValue += 1
                            viewModel.modifyMap(
                                selectedSubj!!,
                                entry.key,
                                oldSubj!!
                            )
                            if (count.intValue == oldSubj?.mapPair?.size) { // User wins
                                userWin.value = true
                                viewModel.resetMap(oldSubj!!)
                                count.intValue = 0
                                increment.intValue = viewModel.getItemRandomly() ?: 0
                                winStreak.intValue = 0
                            } else {
                                if (increment.intValue == viewModel.selectedSubj.value?.mapPair?.size?.minus(
                                        1
                                    ) || viewModel.selectedSubj.value?.mapPair?.size == 1
                                ) {
                                    increment.intValue = 0
                                }
                            }
                        } else {
                            Toast.makeText(loc, "Incorrect", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.width(100.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.Secondary)
                    )
                ) {
                    Text(text = "Submit", fontSize = 16.sp)
                }
            } else {
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (entry != null) {
            TextField(
                value = input.value,
                onValueChange = { input.value = it },
                placeholder = {
                    Text(
                        text = "Type here...",
                        color = Color.White,
                        fontSize = 16.sp,
                    )
                },
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.White
                ),
                textStyle = TextStyle(
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Win streak: ${winStreak.intValue}",
                fontStyle = FontStyle.Italic,
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "© 2024 Brandon Duran",
                    fontStyle = FontStyle.Italic,
                    color = Color.White.copy(alpha = 0.4F)
                )
            }
        }
    }
    if (viewModel.collections.value?.isEmpty() == true) {
        IsCollectionEmpty(collections = collections, navController = navController)
    }
    IsOneDict(winStreak = winStreak, isOnePair = isOnePair, navController = navController)
    UserHint(hint = hint, entry = entry)
    UserWin(userWin = userWin)
    ExitConfirmation(navController = navController, showExitDialog = showExitDialog, viewModel = viewModel, oldSubj = oldSubj)
}


