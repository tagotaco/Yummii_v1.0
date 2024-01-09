package com.example.yummii_v10.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
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

@Composable
fun RecipeDetail(title: String,
                 recipeId: String?,
                 navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5ED)),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = title + recipeId,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }
        }

    }
}

@Composable
@Preview
fun RecipeDetailPreview(){
    val navController = rememberNavController()
    RecipeDetail("Detail", recipeId = "1", navController = navController)
}