package com.example.yummii_v10.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Define the data class outside to be globally accessible if needed
data class Item(
    val text: String,
    var isCrossedOut: Boolean
)

@Composable
fun Shopping(title: String) {
    val items = remember {
        mutableStateListOf(
            Item("Milk", false),
            Item("Eggs", false),
            Item("Chicken wings", false),
            Item("Paprika", false),
            Item("Coriander", false),
            Item("Bread", false)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5ED))
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier.padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Shopping List",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF372524),
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { items.clear() },
                modifier = Modifier
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Clear List",
                    tint = Color(0xFFD86721)
                )
            }
        }

        items.forEachIndexed { index, item ->
            var isCrossedOut by remember { mutableStateOf(item.isCrossedOut) }
            StrikethroughText(
                text = item.text,
                fontSize = 24.sp,
                color = Color(0xFF372524),
                isStrikethrough = isCrossedOut,
                strikethroughColor = Color(0xFFD86721),
                strikethroughThickness = 2.dp, // Adjust thickness as needed
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isCrossedOut = !isCrossedOut
                        items[index] = item.copy(isCrossedOut = isCrossedOut)
                    }
                    .padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun StrikethroughText(
    text: String,
    fontSize: TextUnit,
    color: Color,
    isStrikethrough: Boolean,
    strikethroughColor: Color,
    strikethroughThickness: Dp,
    modifier: Modifier = Modifier
) {

    Text(
        text = text,
        fontSize = fontSize,
        color = color,
        modifier = modifier,
        textDecoration = if (isStrikethrough) TextDecoration.LineThrough else TextDecoration.None
    )
}

@Composable
@Preview(showBackground = true)
fun ShoppingScreenPreview() {
    Shopping("Shopping List")
}
