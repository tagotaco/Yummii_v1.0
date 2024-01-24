package com.example.yummii_v10.View

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.outlined.ShoppingCartCheckout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.yummii_v10.Model.api.api.Ingredient
import com.example.yummii_v10.Model.api.api.recipeDetail.MockRecipeInfoViewModel
import com.example.yummii_v10.Model.api.api.recipeDetail.RecipeInfoViewModelInterface
import com.example.yummii_v10.R
import com.example.yummii_v10.View.component.BackButton
import kotlin.math.roundToInt

@Composable
fun RecipeDetail(
    recipeId: String?,
    recipeInfoViewModel: RecipeInfoViewModelInterface,
    navController: NavHostController
) {

    val recipe by recipeInfoViewModel.recipe.observeAsState()

    // Fetch recipe information when the composable enters the composition
    LaunchedEffect(recipeId) {
        recipeId?.toIntOrNull()?.let {
            recipeInfoViewModel.fetchRecipeInformation(it)
        }
    }

    LazyColumn(
        modifier = Modifier
            .background(Color(0xFFFFF5ED))
            .padding(15.dp),
    ) {
        item { BackButton(navController) }

        recipe?.let { rec ->
            item { TitleSection(rec.title) }
            item { ImageSection(rec.image) }
            item { AuthorSection(rec.author) }
            item {
                RecipeInfoSection(
                    readyInMinutes = rec.readyInMinutes,
                    servings = rec.servings,
                    spoonacularScore = rec.spoonacularScore
                )
            }

            item {
                AddToFavoritesButton(onClick = {
                    // TODO: Add this menu into Favorite List
                })
            }

            item {
                IngredientsCard(
                    ingredientsList = rec.extendedIngredients,
                    onAddToShoppingList = {
                        // TODO: Define the action for adding to the shopping list
                    }
                )
            }

            item { InstructionsCard(rec.instructions.split("\n")) }
        }
    }

}


@Composable
fun TitleSection(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = Color(0xFFAB5C1F),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
fun ImageSection(imageUrl: String?) {
    imageUrl?.let { url ->
        Image(
            painter = rememberImagePainter(
                data = url,
                builder = {
                    placeholder(R.drawable.error_image)
                    error(R.drawable.error_image)
                }
            ),
            contentDescription = "Recipe Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun AuthorSection(author: String?) {
    Text(
        text = "Author: " + (author ?: "Unknown Author"),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF372524),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun RecipeInfoSection(readyInMinutes: Int?, servings: Int?, spoonacularScore: Double?) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = "Duration: ${readyInMinutes ?: "N/A"} minutes",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF372524),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Difficulty: Easy", // TODO: Change from just text to if the ingredients are more than 10 to difficult
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF372524),
            modifier = Modifier.weight(1f)
        )
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = "Servings: ${servings ?: "N/A"}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF372524),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Popularity: ${spoonacularScore?.toInt() ?: "N/A"} points",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF372524),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AddToFavoritesButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFF5ED),
            contentColor = Color(0xFFD86721)
        ),
        border = BorderStroke(3.dp, Color(0xFFD86721)), // Adjust the button's border
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.End)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.AddCircleOutline,
                contentDescription = "Add to favorites",
                modifier = Modifier.size(24.dp) // Size of the icon
            )
            Spacer(Modifier.size(4.dp)) // Space between the icon and the text
            Text(
                text = "Add to favorites",
                fontSize = 16.sp,
                modifier = Modifier.padding(2.dp) // Padding around the text for proper spacing
            )
        }
    }
}



@Composable
fun IngredientsCard(ingredientsList: List<Ingredient>, onAddToShoppingList: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .border(2.dp, Color(0xFFD86721))
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ingredients:",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD86721),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                )

                //TODO: Add selected ingredients to Shopping cart.
                TextButton(
                    onClick = onAddToShoppingList
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCartCheckout,
                        contentDescription = "Add to shopping list",
                        tint = Color(0xFFD86721)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Add to shopping list", color = Color(0xFFD86721))
                }
            }

            HorizontalDivider(
                color = Color(0xFFD86721),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            ingredientsList.forEach { ingredient ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                ) {

                    var isChecked by remember { mutableStateOf(false) }

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Transparent, shape = RoundedCornerShape(2.dp))
                            .border(2.dp, Color(0xFFD86721), shape = RoundedCornerShape(2.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { isChecked = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFFD86721),
                                uncheckedColor = Color.Transparent,
                                checkmarkColor = Color.White)
                        )
                    }

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = "${ingredient.amount.roundToInt()} ${ingredient.measures.metric.unitLong} ${ingredient.name}"
                    )
                }
            }
        }
    }
}


@Composable
fun InstructionsCard(instructions: List<String>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Instructions:",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFFD86721), // Example color for the heading
            modifier = Modifier.padding(bottom = 16.dp)
        )

        instructions.forEachIndexed { index, instruction ->
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            Color(0xFFD86721),
                            shape = CircleShape
                        ) // Circle background color
                        .border(2.dp, Color(0xFFD86721), shape = CircleShape) // Border color
                ) {
                    Text(
                        text = "${index + 1}",
                        //style = MaterialTheme.typography.subtitle1,
                        color = Color(0xFFFFFFFF) // Text color inside the circle
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = instruction,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 3.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RecipeDetailPreview() {
    val navController = rememberNavController()
    val recipeInfoViewModel = MockRecipeInfoViewModel() // Create an instance of MockRecipeInfoViewModel

    RecipeDetail(
        recipeId = "1",
        recipeInfoViewModel = recipeInfoViewModel, // Pass the instance here
        navController = navController
    )
}