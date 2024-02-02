package com.example.yummii_v10.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yummii_v10.view.component.RecipeCard
import com.example.yummii_v10.ViewModel.RecipeViewModel

@Composable
fun Recipe(
    title: String,
    recipeViewModel: RecipeViewModel = viewModel(),
    navController: NavHostController,
    query: String?
) {
    // Check if query is not null and not blank then fetch based on query
    // Else fetch random recipes
    LaunchedEffect(key1 = query) {
        if (!query.isNullOrBlank()) {
            // Fetch recipes based on the query
            recipeViewModel.fetchRecipesBasedOnQuery(query)
        } else {
            // Fetch random recipes
            recipeViewModel.fetchRandomRecipes()
        }
    }

    // Observe LiveData from the ViewModel
    val recipes by recipeViewModel.recipesLiveData.observeAsState(initial = emptyList())

    // Keep the last stage of the page
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5ED))
            .padding(15.dp)
    ) {
        Text(
            text = if (!query.isNullOrBlank()) "Search result for \"$query\"" else "Recipes",
            style = MaterialTheme.typography.headlineLarge,
            color = Color(0xFFFCAB64)
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Text description
        Text(
            text = if (!query.isNullOrBlank())
                "These recipes are the search results for \"$query\""
            else
                "Here are our recommendations for your delicious recipes. Try some of them or you can search for a recipe after your preferences.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFFAB5C1F)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(state = listState) {
            items(items = recipes) { recipe ->
                RecipeCard(recipe = recipe, navController = navController, recipe.id)
            }
        }
    }
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