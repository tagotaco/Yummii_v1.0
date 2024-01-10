package com.example.yummii_v10.ViewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yummii_v10.Model.api.api.RandomRecipeResponse
import com.example.yummii_v10.Model.api.api.RetrofitClient
import com.example.yummii_v10.Model.api.api.SpoonacularService
import com.example.yummii_v10.Model.api.api.VisualizationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel : ViewModel() {

    private val api = RetrofitClient.retrofit.create(SpoonacularService::class.java)
    // LiveData to hold the list of recipes
    val randomRecipesLiveData = MutableLiveData<List<VisualizationResponse>>()

    fun getRandomRecipe() {
        val call = api.getRandomRecipe("d1d0b9b53205452090604f02ea3ebeb2", number = 50)
        call.enqueue(object : Callback<RandomRecipeResponse> {
            override fun onResponse(call: Call<RandomRecipeResponse>, response: Response<RandomRecipeResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val randomRecipeResponse = response.body()!!
                    // Log each image URL
                    Log.d("API Response", "Response: $randomRecipeResponse")
                    // Update LiveData
                    randomRecipesLiveData.value = randomRecipeResponse.recipes
                } else {
                    errorLiveData.value = "Error: ${response.code()}" // Or any other error handling
                }
            }
            //Handle failure
            val errorLiveData = MutableLiveData<String>()

            override fun onFailure(call: Call<RandomRecipeResponse>, t: Throwable) {
                errorLiveData.value = t.message ?: "Unknown Error"
            }
        })
    }
}