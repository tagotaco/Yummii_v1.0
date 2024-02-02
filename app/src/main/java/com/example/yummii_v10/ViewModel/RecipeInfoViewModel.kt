package com.example.yummii_v10.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yummii_v10.Model.api.api.Recipe
import com.example.yummii_v10.Model.api.api.recipeDetail.RecipeInfoViewModelInterface
import com.example.yummii_v10.Model.api.api.recipeDetail.RecipeRepository
import kotlinx.coroutines.launch

//class RecipeInfoViewModel(private val repository: RecipeRepository) : ViewModel()
class RecipeInfoViewModel(private val repository: RecipeRepository) : ViewModel(), RecipeInfoViewModelInterface {
    private val _recipe = MutableLiveData<Recipe>()
    override val recipe: LiveData<Recipe> get() = _recipe

    override fun fetchRecipeInformation(recipeId: Int) {
        viewModelScope.launch {
            try {
                val apiKey = "767f89c0cd564a5592defdd854ea7701"
                val result = repository.getRecipeInformation(recipeId, apiKey)
                _recipe.value = result
            } catch (e: Exception) {
                // Handle the exception
                Log.e("RecipeInfoViewModel", "Error fetching recipe information: ${e.message}")
            }
        }
    }
}


