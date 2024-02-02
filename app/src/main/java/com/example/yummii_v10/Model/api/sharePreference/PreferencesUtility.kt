package com.example.yummii_v10.Data.sharePreference

import android.content.Context

object PreferencesUtility {

    private const val PREFS_NAME = "recipePreferences"
    private const val FAVORITES_KEY = "favoriteRecipes"

    fun saveFavoriteRecipe(context: Context, recipeId: String) {
        val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val favorites = preferences.getStringSet(FAVORITES_KEY, mutableSetOf()) ?: mutableSetOf()
        with(favorites) {
            add(recipeId)
            preferences.edit().putStringSet(FAVORITES_KEY, this).apply()
        }
    }

    fun getFavoriteRecipes(context: Context): Set<String> {
        val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return preferences.getStringSet(FAVORITES_KEY, mutableSetOf()) ?: mutableSetOf()
    }

    fun removeFavoriteRecipe(context: Context, recipeId: String) {
        val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val favorites = preferences.getStringSet(FAVORITES_KEY, mutableSetOf()) ?: mutableSetOf()
        with(favorites) {
            if (remove(recipeId)) {
                preferences.edit().putStringSet(FAVORITES_KEY, this).apply()
            }
        }
    }
}
