package com.example.yummii_v10.Model.api.api.recipeDetail

import androidx.lifecycle.LiveData
import com.example.yummii_v10.Model.api.api.Recipe

interface RecipeInfoViewModelInterface {
    val recipe: LiveData<Recipe>
    fun fetchRecipeInformation(recipeId: Int)
}