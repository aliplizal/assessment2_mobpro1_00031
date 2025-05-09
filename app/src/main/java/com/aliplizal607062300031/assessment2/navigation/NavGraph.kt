package com.aliplizal607062300031.assessment2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aliplizal607062300031.assessment2.ui.screen.DetailScreen
import com.aliplizal607062300031.assessment2.ui.screen.MainScreen
import com.aliplizal607062300031.assessment2.ui.screen.RecycleBinScreen
import com.aliplizal607062300031.assessment2.ui.screen.KEY_ID_BUKU

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_BUKU) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_BUKU)
            DetailScreen(navController, id)
        }
        composable("recycleBin") {
            RecycleBinScreen(navController)
        }
    }
}
