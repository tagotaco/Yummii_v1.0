package com.example.yummii_v10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yummii_v10.Model.api.api.RetrofitClient
import com.example.yummii_v10.Model.api.api.recipeDetail.RecipeInfoViewModelFactory
import com.example.yummii_v10.Model.api.api.recipeDetail.RecipeInfoViewModelInterface
import com.example.yummii_v10.Model.api.api.recipeDetail.RecipeRepository
import com.example.yummii_v10.view.FavoriteUI
import com.example.yummii_v10.view.Homepage
import com.example.yummii_v10.view.Recipe
import com.example.yummii_v10.view.RecipeDetail
import com.example.yummii_v10.view.Shopping
import com.example.yummii_v10.view.components.nav.BottomNav
import com.example.yummii_v10.view.components.nav.Screen
import com.example.yummii_v10.ViewModel.RecipeViewModel
import com.example.yummii_v10.ui.theme.Yummii_v10Theme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            window.statusBarColor = getColor(R.color.CocoaBrown)
            window.navigationBarColor = getColor(R.color.CocoaBrown)
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
    val recipeViewModel: RecipeViewModel = viewModel()
    Scaffold(
        bottomBar = {

            BottomNav(navController.currentBackStackEntry?.destination?.route ?: "",
                navController = navController,
                recipeViewModel = recipeViewModel)
        }
    ) { innerPadding ->
        NavHost(
            navController, startDestination = Screen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable("homepage") { Homepage("Home", navController) }

            composable("recipe") {
                Recipe("Recipe", recipeViewModel, navController, null)
            }

            composable("recipe/{query}") { backStackEntry ->
                val query = backStackEntry.arguments?.getString("query") ?: ""
                Recipe("Recipe", recipeViewModel, navController, query)
            }

            composable("RecipeDetail/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                val spoonacularService = RetrofitClient.service
                val repository = RecipeRepository(spoonacularService)
                val recipeInfoViewModel: RecipeInfoViewModelInterface = viewModel(factory = RecipeInfoViewModelFactory(repository))

                RecipeDetail(id, recipeInfoViewModel, navController)
            }

            composable("favorite") { FavoriteUI("favorite", navController,recipeViewModel ) }
            composable("shopping") { Shopping("shopping") }
            }
        }
}
