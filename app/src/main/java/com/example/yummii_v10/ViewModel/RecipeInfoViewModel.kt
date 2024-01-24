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
                val apiKey = "d1d0b9b53205452090604f02ea3ebeb2"
                val result = repository.getRecipeInformation(recipeId, apiKey)
                _recipe.value = result
            } catch (e: Exception) {
                // Handle the exception
                Log.e("RecipeInfoViewModel", "Error fetching recipe information: ${e.message}")
            }
        }
    }
}


