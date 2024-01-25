package com.example.yummii_v10.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yummii_v10.View.component.SliderView

@Composable
fun Homepage(title: String, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5ED)),
        contentAlignment = Alignment.Center
    ) {
        /*Text(
            text = title,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )*/

        SliderView(navController = navController)
    }
}

@Composable
@Preview
fun HomepagePreview(){
    val navController = rememberNavController()
   Homepage("Home", navController)
}