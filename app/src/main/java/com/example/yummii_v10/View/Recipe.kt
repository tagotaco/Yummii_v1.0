package com.example.yummii_v10.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yummii_v10.View.component.RecipeCard

@Composable
fun Recipe(
    title: String,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5ED)),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn {
            items(10) { index -> // Renamed 'id' to 'index' to avoid any conflict
                RecipeCard(id = index + 1, navController = navController)
            }
        }
    }
}

@Composable
@Preview
fun RecipeScreenPreview() {
    val navController = rememberNavController()
    Recipe(
        title = "Recipe",
        navController = navController
    )
}