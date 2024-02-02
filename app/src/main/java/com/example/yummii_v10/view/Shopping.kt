package com.example.yummii_v10.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Shopping(title: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5ED))
            .padding(15.dp)
    ) {

        Column(
            modifier = Modifier
                .background(Color(0xFFFFF5ED))
                .padding(20.dp)) {
            Text(
                text = title,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF372524)
            )
        }

    }
}

@Composable
@Preview
fun ShoppingScreenPreview(){

    Shopping("Shopping List")
}