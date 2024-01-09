package com.example.yummii_v10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yummii_v10.View.FavoriteUI
import com.example.yummii_v10.View.Homepage
import com.example.yummii_v10.View.Recipe
import com.example.yummii_v10.View.RecipeDetail
import com.example.yummii_v10.View.Shopping
import com.example.yummii_v10.View.components.nav.BottomNav
import com.example.yummii_v10.View.components.nav.Screen
import com.example.yummii_v10.ui.theme.Yummii_v10Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Yummii_v10Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomNavUI()
                }
            }
        }
    }
}

@Composable
fun BottomNavUI() {
    val navController = rememberNavController()
    //val recipeViewModel: RecipeViewModel = viewModel()

    Scaffold(
        bottomBar = {
            BottomNav(navController.currentBackStackEntry?.destination?.route ?: "") {
                navController.navigate(it)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController, startDestination = Screen.HomePage.route,
            Modifier.padding(innerPadding)
        ) {
            composable("homepage") { Homepage("Home") }

            composable("recipe") { Recipe("Recipe", navController)}

            composable("RecipeDetail/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                RecipeDetail("Detail", recipeId = id, navController)
            }

            composable("favorite") { FavoriteUI("favorite") }
            composable("shopping") { Shopping("shopping") }
        }
    }
}

