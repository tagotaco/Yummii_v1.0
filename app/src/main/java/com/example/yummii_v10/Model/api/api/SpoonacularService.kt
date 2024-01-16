package com.example.yummii_v10.Model.api.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularService {
    @POST("recipes/visualizeRecipe")
    fun visualizeRecipe(
        @Query("apiKey") apiKey: String,
        @Query("id") id: Int,
        @Query("title") title: String,
        @Query("servings") servings: Int,
        @Query("readyInMinutes") readyInMinutes: Int,
        @Query("image") image: String,
        @Query("author") author: String,
        @Query("instructions") instructions: String
    ): Call<VisualizationResponse>

    @GET("recipes/random")
    fun getRandomRecipe(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int
    ): Call<RandomRecipeResponse> // Define RandomRecipeResponse based on the expected response

    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(@Path("id") recipeId: Int): Recipe

}