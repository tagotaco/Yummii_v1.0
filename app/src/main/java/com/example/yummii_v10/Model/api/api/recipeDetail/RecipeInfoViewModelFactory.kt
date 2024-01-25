package com.example.yummii_v10.Model.api.api.recipeDetail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

