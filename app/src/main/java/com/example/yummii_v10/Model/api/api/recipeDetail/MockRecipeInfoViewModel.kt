package com.example.yummii_v10.Model.api.api.recipeDetail

import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yummii_v10.Model.api.api.Ingredient
import com.example.yummii_v10.Model.api.api.Measure
import com.example.yummii_v10.Model.api.api.Measures
import com.example.yummii_v10.Model.api.api.Recipe

class MockRecipeInfoViewModel : RecipeInfoViewModelInterface {
    private val _recipe = MutableLiveData<Recipe>()
    override val recipe: LiveData<Recipe> get() = _recipe

    private val mockRecipes = listOf(
        Recipe(
            id = 716429,
            title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
            image = "https://spoonacular.com/recipeImages/716429-556x370.jpg",
            servings = 2,
            readyInMinutes = 45,
            spoonacularScore = 83.0,
            author = "Full Belly Sisters",
            extendedIngredients = listOf(
                Ingredient(
                    aisle = "Milk, Eggs, Other Dairy",
                    amount = 1.0,
                    consitency = "solid", // Corrected typo here
                    id = 1001,
                    image = "butter-sliced.jpg",
                    measures = Measures(
                        metric = Measure(amount = 1.0, unitLong = "Tbsp", unitShort = "Tbsp"),
                        us = Measure(amount = 1.0, unitLong = "Tbsp", unitShort = "Tbsp")
                    ),
                    name = "butter",
                    original = "1 tbsp butter",
                    unit = "tbsp",
                    meta = listOf() // Added meta field
                ),
            ),
            instructions = "Instructions..."
        )
    )


    override fun fetchRecipeInformation(recipeId: Int) {
        //Check if Recipe Id is valid
        if (recipeId <= 0) {
            Log.e("MockRecipeInfoViewModel", "Invalid recipeId: $recipeId")
            return
        }

        // Simulate network delay
        android.os.Handler(Looper.getMainLooper()).postDelayed({

            val mockRecipe = getMockRecipeById(recipeId)

            if (mockRecipe != null) {
                _recipe.value = mockRecipe
                Log.d("MockRecipeInfoViewModel", "Recipe updated for id: $recipeId")
            } else {
                Log.e("MockRecipeInfoViewModel", "No mock recipe found for id: $recipeId")
            }
        }, 1000) // 1-second delay

    }

    private fun getMockRecipeById(recipeId: Int): Recipe? {
        return mockRecipes.find { it.id == recipeId }
    }

}