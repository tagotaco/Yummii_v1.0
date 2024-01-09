package com.example.yummii_v10.View.components.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen (val route: String, val icon: ImageVector) {
    HomePage("homepage", Icons.Default.Home),
    Recipe("recipe", Icons.Outlined.List),
    Favorite("favorite", Icons.Default.Favorite),
    Shopping("shopping", Icons.Default.ShoppingCart)
}