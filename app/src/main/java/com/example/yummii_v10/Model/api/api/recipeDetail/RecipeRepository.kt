package com.example.yummii_v10.Model.api.api.recipeDetail

import com.example.yummii_v10.Model.api.api.Recipe
import com.example.yummii_v10.Model.api.api.SpoonacularService

class RecipeRepository(private val service: SpoonacularService) {
    suspend fun getRecipeInformation(recipeId: Int): Recipe {
        return service.getRecipeInformation(recipeId)
    }
}
