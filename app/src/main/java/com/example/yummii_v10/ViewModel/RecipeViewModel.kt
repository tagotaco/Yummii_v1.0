package com.example.yummii_v10.ViewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.yummii_v10.Model.api.api.RandomRecipeResponse
import com.example.yummii_v10.Model.api.api.Recipe
import com.example.yummii_v10.Model.api.api.RetrofitClient
import com.example.yummii_v10.Model.api.api.SpoonacularService
import com.example.yummii_v10.Model.api.api.VisualizationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel(private val state: SavedStateHandle) : ViewModel() {

    private val api = RetrofitClient.retrofit.create(SpoonacularService::class.java)

    // LiveData to hold the list of recipes, initially empty
    private val _randomRecipesLiveData = MutableLiveData<List<VisualizationResponse>>(emptyList())
    val randomRecipesLiveData: LiveData<List<VisualizationResponse>> = _randomRecipesLiveData

    // LiveData to handle errors
    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    // Fetch recipes when ViewModel is created
    init {
        getRandomRecipe()
    }

    // Use SavedStateHandle to save and retrieve the query
    var query: String?
        get() = state["QUERY_KEY"]
        set(value) {
            state["QUERY_KEY"] = value
        }

    init {
        // Fetch recipes based on the saved query
        getRandomRecipe(query)
    }

    fun getRandomRecipe(query: String? = this.query) {
        val call = api.getRandomRecipe("d1d0b9b53205452090604f02ea3ebeb2", number = 50)
        call.enqueue(object : Callback<RandomRecipeResponse> {
            override fun onResponse(call: Call<RandomRecipeResponse>, response: Response<RandomRecipeResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val randomRecipeResponse = response.body()!!
                    _randomRecipesLiveData.value = randomRecipeResponse.recipes
                } else {
                    _errorLiveData.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<RandomRecipeResponse>, t: Throwable) {
                _errorLiveData.value = t.message ?: "Unknown Error"
            }
        })
    }
}