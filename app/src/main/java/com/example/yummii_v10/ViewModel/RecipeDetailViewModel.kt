package com.example.yummii_v10.ViewModel

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
                val result = repository.getRecipeInformation(recipeId)
                _recipe.value = result
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }
}

