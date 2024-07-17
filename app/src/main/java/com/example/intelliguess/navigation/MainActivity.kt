package com.example.intelliguess.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.intelliguess.IntelliGuessViewModel
import com.example.intelliguess.ui.theme.IntelliGuessTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                    val vm = hiltViewModel<IntelliGuessViewModel>()
                    // Create an instance of NavController
                    val navController = rememberNavController()
                    SetupNavGraph(
                        navController = navController,
                        vm
                    )
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (viewModel.isInHomeScreen.value == true) {
            viewModel.resetMap() // Reset the map when the user exit from the app
        }
    }
}





