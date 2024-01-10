package com.example.yummii_v10.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yummii_v10.View.component.RecipeCard

//TODO: Fix query parameters
//TODO: Connect to Api

@Composable
fun Recipe(
    title: String,
    navController: NavHostController,
    query: String?
) {
    // Check if query is null or empty to decide on random recipes
    /*al shouldShowRandomRecipes = query.isNullOrEmpty()
    val recipes = if (shouldShowRandomRecipes) {
        // Generate or fetch random recipes
    } else {
        // Fetch recipes based on the query
    }*/


    // Test Recipe card
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5ED)),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn {
            items(10) { index -> // Renamed 'id' to 'index' to avoid any conflict
                RecipeCard(id = index + 1, navController = navController)
            }
        }
    }
    // End test recipe card
}

@Composable
@Preview
fun RecipeScreenPreview() {
    val navController = rememberNavController()
    Recipe(
        title = "Recipe",
        navController = navController,
        query = null
    )
}