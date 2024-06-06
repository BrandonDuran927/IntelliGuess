package com.example.intelliguess.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.intelliguess.IntelliGuessViewModel
import com.example.intelliguess.presentation.IntelliGuessCollection
import com.example.intelliguess.ui.theme.IntelliGuessTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<IntelliGuessViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntelliGuessTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController: NavHostController = rememberNavController()
                    SetupNavGraph(
                        navController = navController,
                        viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val showGuide = rememberSaveable { mutableStateOf(true) }
    val buttonPosition = remember { mutableStateOf(Offset.Zero) }
    val buttonSize = remember { mutableStateOf(Size.Zero) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { /* Navigate to collections */ },
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        val position = coordinates.localToRoot(Offset.Zero)
                        buttonPosition.value = position
                        buttonSize.value = coordinates.size.toSize()
                    }
            ) {
                Text(text = "Proceed to Collections")
            }
        }

        if (showGuide.value) {
            GuideOverlay(
                onDismiss = { showGuide.value = false },
                targetPosition = buttonPosition.value,
                targetSize = buttonSize.value
            )
        }
    }
}

@Composable
fun GuideOverlay(
    onDismiss: () -> Unit,
    targetPosition: Offset,
    targetSize: Size
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))
            .clickable { onDismiss() }
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawRect(
                color = Color.Transparent,
                topLeft = targetPosition,
                size = targetSize,
                blendMode = BlendMode.Clear
            )
        }
        Text(
            text = "Click here to proceed to the collections",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    MyApp()
}





