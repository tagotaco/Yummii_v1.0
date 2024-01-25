package com.example.yummii_v10.View.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.yummii_v10.Model.api.api.VisualizationResponse
import com.example.yummii_v10.R



@OptIn(ExperimentalCoilApi::class)
@Composable
fun RecipeCard(
    recipe: VisualizationResponse,
    navController: NavHostController,
    id: Int,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.White)
                .padding(5.dp)
        ) {

                val imageUrl = recipe.image

                Image(
                    painter = rememberImagePainter(
                        data = imageUrl,
                        builder = {
                          listener(onError = { _, throwable ->
                                Log.e("RecipeCard", "Error loading image: $throwable")
                            })
                            placeholder(R.drawable.error_image)
                            error(R.drawable.error_image)
                        }
                    )
                    ,
                    contentDescription = "Recipe Image",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFFF4F00)
                    )

                    IconAndText(text = "Servings: " + recipe.servings)
                    IconAndText(text = "Duration: " + recipe.readyInMinutes + " minutes")
                    IconAndText(text = "Author: " + recipe.author)
                    Button(
                        onClick = {
                             navController.navigate("RecipeDetail/${recipe.id}")
                        },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD86721)
                        )
                    ) {
                        Text("More..", color = Color.White)
                    }
                }
            }
    }
}

@Composable
fun IconAndText(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(text, style = MaterialTheme.typography.bodyMedium)
        }
}

@Composable
@Preview
fun RecipeCardPreview() {
    val navController = rememberNavController()

    //Mock data
    val recipeData = VisualizationResponse(
    id = 1,
    title = "Mock Recipe Title",
    image = "https://via.placeholder.com/150",
    servings = 4,
    readyInMinutes = 30,
    author = "John Doe",
    ingredients = "",
    instructions = "",
        summary ="",
    )

RecipeCard(navController = navController,recipe = recipeData, id = 1)

}