package com.example.yummii_v10.view

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.yummii_v10.Data.sharePreference.PreferencesUtility
import com.example.yummii_v10.Data.sharePreference.PreferencesUtility.removeFavoriteRecipe
import com.example.yummii_v10.Model.api.api.Recipe
import com.example.yummii_v10.Model.api.api.VisualizationResponse
import com.example.yummii_v10.R
import com.example.yummii_v10.ViewModel.RecipeViewModel

@Composable
fun FavoriteUI(
    title: String,
    navController: NavHostController,
    recipeViewModel: RecipeViewModel = viewModel(),
    context: Context = LocalContext.current
) {
    val recipeViewModel: RecipeViewModel = viewModel()

    val favoriteRecipeIds = PreferencesUtility.getFavoriteRecipes(context)
    //val favoriteRecipes by recipeViewModel.favoriteRecipesLiveData.observeAsState(emptyList())
    val favoriteRecipes by recipeViewModel.favoriteRecipesLiveData.observeAsState(initial = emptyList())

    LaunchedEffect(favoriteRecipeIds) {
        val idsList = favoriteRecipeIds.mapNotNull { it.toIntOrNull() }
        recipeViewModel.fetchFavoriteRecipes(idsList)
    }

    LaunchedEffect(favoriteRecipes) {
        Log.d("FavoriteUI", "Current favorite recipes: $favoriteRecipes")
    }/**/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5ED))
            .padding(15.dp)
    ) {
        Text(
            text = "Favorite List",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        LazyColumn {
            items(items = favoriteRecipes) { recipe ->
                FavoriteCard(
                    recipe = recipe.toVisualizationResponse(),
                    navController = navController,
                    id = recipe.id
                )
            }
        }

    }
}

@Composable
fun FavoriteCard(
    recipe: VisualizationResponse,
    navController: NavHostController,
    context: Context = LocalContext.current,
    id: Int,
    recipeViewModel: RecipeViewModel = viewModel()

) {
    // Remove favorite warnings
    var showDialog by remember { mutableStateOf(false) }

    // Create a mutable state to store the recipe to remove
    var recipeToRemove by remember { mutableStateOf<VisualizationResponse?>(null) }

    // Function to show the confirmation dialog
    fun showConfirmationDialog(recipe: VisualizationResponse) {
        recipeToRemove = recipe
        showDialog = true
    }

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
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFFF4F00),
                        modifier = Modifier.width(180.dp)
                    )
                    Spacer(Modifier.weight(1f))

                    //TODO: Add Bin icon
                    IconButton(
                        onClick = { PreferencesUtility.removeFavoriteRecipe(context, recipe.id.toString()) },//showConfirmationDialog(recipe)
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remove Recipe",
                            tint = Color(0xFFD86721)
                        )
                    }

                }
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


            //TODO: Warnning massage when deleting
            /*if (showDialog && recipeToRemove != null) {
                AlertDialog(
                    onDismissRequest = {
                        // Reset the recipeToRemove when the dialog is dismissed
                        recipeToRemove = null
                        showDialog = false
                    },
                    title = {
                        Text(text = "Remove Recipe")
                    },
                    text = {
                        Text(text = "Are you sure you want to remove this recipe from favorites?")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                // Remove the recipe and dismiss the dialog
                                removeFavoriteRecipe(context, recipe.id.toString())
                                recipeToRemove = null
                                showDialog = false
                            }
                        ) {
                            Text(text = "Yes")
                        }

                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                // Dismiss the dialog without removing the recipe
                                recipeToRemove = null
                                showDialog = false
                            }
                        ) {
                            Text(text = "No")
                        }
                    }
                )
            }*/

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

fun Recipe.toVisualizationResponse(): VisualizationResponse {
    return VisualizationResponse(
        id = this.id,
        title = this.title,
        image = this.image,
        servings = this.servings,
        readyInMinutes = this.readyInMinutes,
        author = this.author,
        ingredients = "",
        instructions = "",
        summary ="",
    )
}

@Composable
@Preview
fun FavoriteUIScreenPreview(){

    FavoriteUI("Favorite",
        navController = rememberNavController(),
        recipeViewModel = viewModel() )
}