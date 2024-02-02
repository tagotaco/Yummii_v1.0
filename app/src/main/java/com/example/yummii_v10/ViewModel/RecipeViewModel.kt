package com.example.yummii_v10.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yummii_v10.Model.api.api.RandomRecipeResponse
import com.example.yummii_v10.Model.api.api.Recipe
import com.example.yummii_v10.Model.api.api.RetrofitClient
import com.example.yummii_v10.Model.api.api.SearchRecipeResponse
import com.example.yummii_v10.Model.api.api.SpoonacularService
import com.example.yummii_v10.Model.api.api.VisualizationResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class RecipeViewModel(private val state: SavedStateHandle) : ViewModel() {

    private val api = RetrofitClient.retrofit.create(SpoonacularService::class.java)

    // LiveData for a list of recipes
    private val _recipesLiveData = MutableLiveData<List<VisualizationResponse>>(emptyList())
    val recipesLiveData: LiveData<List<VisualizationResponse>> = _recipesLiveData

    // LiveData for a single recipe
    private val _recipeLiveData = MutableLiveData<Recipe>()
    val recipeLiveData: LiveData<Recipe> = _recipeLiveData

    // LiveData for fixed recipes
    private val _fixedRecipesLiveData = MutableLiveData<List<Recipe>>(emptyList())
    val fixedRecipesLiveData: LiveData<List<Recipe>> = _fixedRecipesLiveData

    private val _favoriteRecipesLiveData = MutableLiveData<List<Recipe>>(emptyList())
    val favoriteRecipesLiveData: LiveData<List<Recipe>> = _favoriteRecipesLiveData


    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData


    // Use SavedStateHandle to save and retrieve the query
    var query: String?
        get() = state["QUERY_KEY"]
        set(value) {
            state["QUERY_KEY"] = value
            fetchRecipes()
        }

    private fun fetchRecipes() {
        query?.let {
            fetchRecipesBasedOnQuery(it)
        } ?: run {
            fetchRandomRecipes()
        }
    }


    fun fetchRandomRecipes() {
        val call = api.getRandomRecipe("767f89c0cd564a5592defdd854ea7701", number = 50)
        call.enqueue(object : Callback<RandomRecipeResponse> {
            override fun onResponse(call: Call<RandomRecipeResponse>, response: Response<RandomRecipeResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    _recipesLiveData.value = response.body()!!.recipes
                } else {
                    _errorLiveData.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<RandomRecipeResponse>, t: Throwable) {
                _errorLiveData.value = t.message ?: "Unknown Error"
            }
        })
    }

    fun fetchRecipesBasedOnQuery(query: String) {
        val call = api.searchRecipes("767f89c0cd564a5592defdd854ea7701", query = query, number = 50, offset = 0)
        call.enqueue(object : Callback<SearchRecipeResponse> {
            override fun onResponse(call: Call<SearchRecipeResponse>, response: Response<SearchRecipeResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    _recipesLiveData.value = response.body()!!.results
                } else {
                    _errorLiveData.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<SearchRecipeResponse>, t: Throwable) {
                _errorLiveData.value = t.message ?: "Unknown Error"
            }
        })
    }


    fun fetchFixedRecipes(ids: List<Int>) {
        viewModelScope.launch {
            val recipes = ids.mapNotNull { id ->
                try {
                    api.getRecipeInformation(id, "767f89c0cd564a5592defdd854ea7701")
                } catch (e: Exception) {
                    Log.e("RecipeViewModel", "Exception fetching recipe", e)
                    null
                }
            }
            // Use postValue to update the LiveData from a background thread
            _fixedRecipesLiveData.postValue(recipes)
        }
    }


    fun fetchRecipeData(recipeId: Int) {
        viewModelScope.launch {
            try {
                // Directly receive the result since it's a suspend function
                val recipe = api.getRecipeInformation(recipeId, "767f89c0cd564a5592defdd854ea7701")
                Log.d("RecipeViewModel", "Recipe fetched: $recipe")
                _recipeLiveData.value = recipe

            } catch (e: HttpException) {
                // Handle the case where the API response is not successful
                Log.e("RecipeViewModel", "API Error: ${e.response()?.errorBody()?.string()}", e)

            } catch (e: Exception) {
                // Handle other exceptions such as network errors, etc.
                Log.e("RecipeViewModel", "Exception fetching recipe", e)
            }
        }
    }

    fun fetchFavoriteRecipes(ids: List<Int>) {
        viewModelScope.launch {
            val recipes = ids.mapNotNull { id ->
                try {
                    api.getRecipeInformation(id, "767f89c0cd564a5592defdd854ea7701").also {
                        Log.d("RecipeViewModel", "Fetched favorite recipe: $it")
                    }
                } catch (e: Exception) {
                    Log.e("RecipeViewModel", "Exception fetching favorite recipe", e)
                    null
                }
            }
            _favoriteRecipesLiveData.postValue(recipes)
        }
    }


}