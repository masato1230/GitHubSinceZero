package com.github.masato1230.githubclienet.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.masato1230.githubclienet.presentation.screens.home.HomeScreen
import com.github.masato1230.githubclienet.presentation.theme.GitHubClienetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubClienetTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = AppRoute.Home) {
                    composable<AppRoute.Home> {
                        HomeScreen()
                    }
                }
            }
        }
    }
}