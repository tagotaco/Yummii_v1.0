package com.example.yummii_v10.Model.api.api.recipeDetail

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.yummii_v10.Model.api.api.RetrofitClient
import com.example.yummii_v10.View.RecipeDetail
import com.example.yummii_v10.ViewModel.RecipeInfoViewModel

class RecipeInfoViewModelFactory(private val repository: RecipeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeInfoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun RecipeDetailScreen(navController: NavHostController, recipeId: String) {
    // Use RetrofitClient to get SpoonacularService instance
    val spoonacularService = RetrofitClient.service
    val repository = RecipeRepository(spoonacularService)

    // Create ViewModel using factory
    val viewModel: RecipeInfoViewModel = viewModel(factory = RecipeInfoViewModelFactory(repository))
    RecipeDetail(
        title = "Recipe Detail",
        recipeId = recipeId,
        recipeInfoViewModel = viewModel,
        navController = navController
    )
}
