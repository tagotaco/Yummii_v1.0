package com.example.yummii_v10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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
import com.example.yummii_v10.ui.theme.Typography
import com.example.yummii_v10.ui.theme.Yummii_v10Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        bottomBar = {
            // Pass only the current route and navController to BottomNav
            BottomNav(navController.currentBackStackEntry?.destination?.route ?: "", navController)
        }
    ) { innerPadding ->
        NavHost(
            navController, startDestination = Screen.HomePage.route,
            Modifier.padding(innerPadding)
        ) {
            composable("homepage") { Homepage("Home") }

            //TODO: Fix recipe cannot take query parameters
            composable("recipe"){Recipe("Recipe", navController, null)}

            /*composable("recipe/{query}") { backStackEntry ->
                val query = backStackEntry.arguments?.getString("query") ?: ""
                Recipe("Recipe", navController, null)
            }*/

            composable("RecipeDetail/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                RecipeDetail("Detail", id, navController)
            }

            composable("favorite") { FavoriteUI("favorite") }
            composable("shopping") { Shopping("shopping") }
        }
    }
}



