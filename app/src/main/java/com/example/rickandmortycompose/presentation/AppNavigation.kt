package com.example.rickandmortycompose.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "rickList") {
        composable("rickList") { RickListScreen(navController) }
        composable("rickDetail/{characterId}") { backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId")
            characterId?.let { RickDetailScreen(characterId = it) }
        }
    }
}