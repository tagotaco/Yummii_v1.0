package com.example.yummii_v10.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yummii_v10.R
import com.example.yummii_v10.view.component.SliderView
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homepage(title: String, navController: NavController) {

    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Yummi",
                        color = Color(0xFFA96C36),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFFFFF5ED)
                )
            )
        },
    ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFFFF5ED)),

            ) {

                SliderView(navController = navController)

                Row (modifier = Modifier
                    .padding(5.dp)
                ){

                    SearchWithPreferences()
                    SearchBar(searchText, onSearchChanged ={ searchText = it } ) {
                        if (searchText.isNotBlank()) {
                            navController.navigate("recipe/$searchText")
                        }
                    }
                }

                CategorySection(onCategoryClick = { category ->
                    navController.navigate("recipe/$category")
                })

            }
        }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: String,
    onSearchChanged: (String) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchChanged,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White, RoundedCornerShape(8.dp)),
        placeholder = { Text(text = "Search for delicious recipes", color = Color.Gray) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                modifier = Modifier.clickable { onSearch() } // Add clickable modifier
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() })
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWithPreferences() {
    Icon(
        modifier = Modifier
            .padding(16.dp)
            .clickable { /* Handle icon click if needed */ },
        imageVector = Icons.Filled.Menu,
        contentDescription = null
    )
}

@Composable
fun CategorySection(onCategoryClick: (String) -> Unit) {
    Text(
        text = "Choose by recipe’s category:",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color(0xFF4A2301),
        modifier = Modifier.padding(horizontal = 25.dp)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(Modifier.weight(1f))
        CategoryItem("Breakfast", painterResource(id = R.drawable.breakfast)) {
            onCategoryClick("Breakfast")
        }
        Spacer(Modifier.weight(1f))
        CategoryItem("Lunch", painterResource(id = R.drawable.lunch)) {
            onCategoryClick("Lunch")
        }
        Spacer(Modifier.weight(1f))

        CategoryItem("Dinner", painterResource(id = R.drawable.dinner)) {
            onCategoryClick("Dinner")
        }
        Spacer(Modifier.weight(1f))

    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(Modifier.weight(1f))

        CategoryItem("Dessert", painterResource(id = R.drawable.dessert)) {
            onCategoryClick("Dessert")
        }
        Spacer(Modifier.weight(1f))

        CategoryItem("Snack", painterResource(id = R.drawable.snack)) {
            onCategoryClick("Snack")
        }
        Spacer(Modifier.weight(1f))

        CategoryItem("Drinks", painterResource(id = R.drawable.drink)) {
            onCategoryClick("Drinks")
        }
        Spacer(Modifier.weight(1f))

    }
}

@Composable
fun CategoryItem(name: String, icon: Painter, onClick: () -> Unit) {
    Image(
        painter = icon,
        contentDescription = name,
        modifier = Modifier
            .size(110.dp)
            .clip(RectangleShape)
            .clickable { onClick() }
    )
}





@Composable
@Preview
fun HomepagePreview(){
    val navController = rememberNavController()
   Homepage("Home", navController)
}