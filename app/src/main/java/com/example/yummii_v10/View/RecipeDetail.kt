package com.example.yummii_v10.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.yummii_v10.Model.api.api.Recipe
import com.example.yummii_v10.Model.api.api.VisualizationResponse
import com.example.yummii_v10.Model.api.api.recipeDetail.MockRecipeInfoViewModel
import com.example.yummii_v10.Model.api.api.recipeDetail.RecipeInfoViewModelInterface
import com.example.yummii_v10.R
import com.example.yummii_v10.View.component.BackButton
import com.example.yummii_v10.View.component.IconAndText
import com.example.yummii_v10.ViewModel.RecipeInfoViewModel

@Composable
fun RecipeDetail(
    title: String,
    recipeId: String?,
    //recipeInfoViewModel: RecipeInfoViewModel,
    recipeInfoViewModel: RecipeInfoViewModelInterface,
    navController: NavHostController
) {
    // Observe the recipe LiveData from the ViewModel
    val recipe by recipeInfoViewModel.recipe.observeAsState()

    // Fetch recipe information when the composable enters the composition
    LaunchedEffect(recipeId) {
        recipeId?.let { id ->
            recipeInfoViewModel.fetchRecipeInformation(id.toInt())
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5ED))
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                BackButton(navController = navController)
            }
        }

        item {
            recipe?.image
                ?.let { imageUrl ->
                    Image(
                        painter = rememberImagePainter(
                            data = imageUrl,
                            builder = {
                                //placeholder(R.drawable.placeholder_image)
                                error(R.drawable.error_image)
                            }
                        ),
                        contentDescription = "Recipe Image",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
        }

        item {
        Text(
            text = recipe?.title ?: "",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
         }


    }
}



@Composable
@Preview(showBackground = true)
fun RecipeDetailPreview() {
    val navController = rememberNavController()
    val recipeInfoViewModel = MockRecipeInfoViewModel() // Create an instance of MockRecipeInfoViewModel

    RecipeDetail(
        title = "Detail",
        recipeId = "1",
        recipeInfoViewModel = recipeInfoViewModel, // Pass the instance here
        navController = navController
    )
}