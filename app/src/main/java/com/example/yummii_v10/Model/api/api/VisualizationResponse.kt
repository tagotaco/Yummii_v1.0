package com.example.yummii_v10.Model.api.api

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class VisualizationResponse(
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val servings: Int,
    @SerializedName("sourceName") // author name is nullable then replace with source name.
    val author: String?,
    val ingredients: String,
    val instructions: String,
    val summary: String
)
data class RandomRecipeResponse(
    val recipes: List<VisualizationResponse>
)

// Data class for recipe descriptions.
data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val servings: Int,
    val readyInMinutes: Int,
    val spoonacularScore: Double,
    val author: String,
    val extendedIngredients: List<Ingredient>,
    val instructions: String,
)

data class Ingredient(
    val id: Int,
    val aisle: String,
    val amount: Double,
    val consitency: String, // There was a typo in 'consitency'
    val image: String,
    val measures: Measures,
    val name: String,
    val original: String,
    val unit: String,
    val meta: List<String> // This can include 'grated', 'chopped', etc.
)

data class Measures(
    val metric: Measure,
    val us: Measure
)

data class Measure(
    val amount: Double,
    val unitLong: String,
    val unitShort: String
)