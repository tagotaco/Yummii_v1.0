package com.example.yummii_v10.Model.api.api

data class VisualizationResponse(
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val servings: Int,
    val author: String,
    val ingredients: String,
    val instructions: String,
)
data class RandomRecipeResponse(
    val recipes: List<VisualizationResponse> // Assuming the response has a list of recipes
)
