package com.example.yummii_v10.Model.api.api.recipeDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yummii_v10.Model.api.api.Recipe

class MockRecipeInfoViewModel : RecipeInfoViewModelInterface {
    private val _recipe = MutableLiveData<Recipe>()
    override val recipe: LiveData<Recipe> get() = _recipe

    init {
        _recipe.value = Recipe(
            // Mock data
            id = 123, // Dummy ID
            title = "Chocolate Cake", // Dummy title
            image = "https://via.placeholder.com/150", // Dummy image URL
            servings = 4, // Dummy number of servings
            readyInMinutes = 45, // Dummy preparation time
            author = "John Doe", // Dummy author name
            ingredients = "Flour, Sugar, Cocoa, Baking Powder, Eggs", // Dummy ingredients
            instructions = "Mix ingredients. Bake for 30 minutes."
        )
    }

    override fun fetchRecipeInformation(recipeId: Int) {
        // Maybe update _recipe with different mock data
    }
}