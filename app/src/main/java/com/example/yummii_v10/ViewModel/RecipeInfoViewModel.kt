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

class RecipeInfoViewModel(private val repository: RecipeRepository) : ViewModel(), RecipeInfoViewModelInterface {
    private val _recipe = MutableLiveData<Recipe>()
    override val recipe: LiveData<Recipe> get() = _recipe

    override fun fetchRecipeInformation(recipeId: Int) {
        viewModelScope.launch {
            try {
                val apiKey = "f79e6a6ee5704e2096edfcdfc739606a"
                val result = repository.getRecipeInformation(recipeId, apiKey)
                _recipe.value = result
            } catch (e: Exception) {

                Log.e("RecipeInfoViewModel", "Error fetching recipe information: ${e.message}")
            }
        }
    }
}


