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
)
data class RandomRecipeResponse(
    val recipes: List<VisualizationResponse>
)

data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val servings: Int,
    val author: String,
    val ingredients: String,
    val instructions: String,
)