package com.example.yummii_v10.View.components.nav

import androidx.compose.material3.*
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNav (currentRoute: String, onNavigate: (String) -> Unit) {
    NavigationBar {
        Screen.values().forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.name) },
                label = { Text(screen.name) },
                selected = currentRoute == screen.route,
                onClick = { onNavigate(screen.route) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}