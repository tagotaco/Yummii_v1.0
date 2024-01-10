package com.example.yummii_v10.View.components.nav

import androidx.compose.material3.*
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
fun BottomNav (currentRoute: String, navController: NavHostController) {

    val contentColor = Color(0xFF372524)
    val inactiveColor = Color(0xFF9E9E9E)

    NavigationBar(
        containerColor = Color(0xFFD86721),
        contentColor = Color(0xFF372524)
    ) {
        Screen.values().forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.name, tint = contentColor) },
                label = { Text(screen.name, color = contentColor) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route)
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