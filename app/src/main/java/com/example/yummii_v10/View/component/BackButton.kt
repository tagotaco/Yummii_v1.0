package com.example.yummii_v10.View.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BackButton(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .size(width = 100.dp, height = 45.dp),
            shape = RoundedCornerShape(size = 5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD86721)
            ),
            contentPadding = PaddingValues(
                start = 0.dp,
                top = 5.dp,
                end = 5.dp,
                bottom = 8.dp
            )
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Icon",
            )
            Spacer(modifier = Modifier.width(8.dp)) // Add space between icon and text
            Text(text = "Back")
        }
    }
}