package com.exemplo.matematicaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exemplo.matematicaapp.ui.screens.ExerciseScreen
import com.exemplo.matematicaapp.ui.screens.HomeScreen
import com.exemplo.matematicaapp.ui.theme.MatemáticaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatemáticaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            HomeScreen(
                                onCategoryClick = { categoryId ->
                                    navController.navigate("exercise/$categoryId")
                                }
                            )
                        }
                        composable("exercise/{categoryId}") { backStackEntry ->
                            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: "arithmetic"
                            ExerciseScreen(
                                categoryId = categoryId,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
