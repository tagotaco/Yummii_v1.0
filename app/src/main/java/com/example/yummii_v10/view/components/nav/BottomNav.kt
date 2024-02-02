package com.example.yummii_v10.view.components.nav

import androidx.compose.material3.*
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.yummii_v10.ViewModel.RecipeViewModel

@Composable
fun BottomNav(
    currentRoute: String,
    navController: NavHostController,
    recipeViewModel: RecipeViewModel // Pass RecipeViewModel as a parameter
) {
    val contentColor = Color(0xFF372524)
    val inactiveColor = Color(0xFF9E9E9E)
    val activeColor = Color(0xFFEFB8C8)

    NavigationBar(containerColor = Color(0xFFD86721), contentColor = contentColor) {
        Screen.values().forEach { screen ->
            NavigationBarItem(
                icon = {
                        Icon(screen.icon, contentDescription = screen.name,
                        tint = contentColor)
                       },
                label = { Text(screen.name, color = contentColor) },
                selected = currentRoute == screen.route,
                onClick = {
                    // Check if the screen is Recipe, if so, reset the query
                    if (screen == Screen.Recipe) {
                        recipeViewModel.query = null
                    }
                    navController.navigate(screen.route) {
                        //TODO: Add options if needed, e.g., popUpTo, launchSingleTop
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = contentColor,
                    unselectedIconColor = inactiveColor,
                    selectedTextColor = contentColor,
                    unselectedTextColor = inactiveColor,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}