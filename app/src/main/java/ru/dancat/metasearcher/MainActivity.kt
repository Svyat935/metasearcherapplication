package ru.dancat.metasearcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.dancat.metasearcher.ui.view.Searching
import ru.dancat.metasearcher.ui.view.Studio

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "searching") {
                composable("searching") { Searching(navController) }
                composable(
                    "studio/{studioId}",
                    arguments = listOf(navArgument("studioId") {
                        type = NavType.StringType
                    })
                ) {
                    val param = it.arguments?.getString("studioId") ?: ""
                    Studio(param)
                }
            }
        }
    }
}