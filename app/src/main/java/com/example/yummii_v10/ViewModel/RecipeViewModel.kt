package com.example.yummii_v10.ViewModel

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
import retrofit2.Response

class RecipeViewModel(private val state: SavedStateHandle) : ViewModel() {

    private val api = RetrofitClient.retrofit.create(SpoonacularService::class.java)

    private val _recipesLiveData = MutableLiveData<List<VisualizationResponse>>(emptyList())
    val recipesLiveData: LiveData<List<VisualizationResponse>> = _recipesLiveData

    // LiveData for fixed recipes
    private val _fixedRecipesLiveData = MutableLiveData<List<Recipe>>(emptyList())
    val fixedRecipesLiveData: LiveData<List<Recipe>> = _fixedRecipesLiveData

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
            searchRecipes(it)
        } ?: run {
            getRandomRecipe()
        }
    }


    private fun getRandomRecipe() {
        val call = api.getRandomRecipe("d1d0b9b53205452090604f02ea3ebeb2", number = 50)
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

    private fun searchRecipes(query: String) {
        val call = api.searchRecipes("d1d0b9b53205452090604f02ea3ebeb2", query = query, number = 50, offset = 0)
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
                    api.getRecipeInformation(id, "d1d0b9b53205452090604f02ea3ebeb2")
                } catch (e: Exception) {
                    null
                }
            }
            _fixedRecipesLiveData.value = recipes
        }
    }

}