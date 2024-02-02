package com.example.yummii_v10.view.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.yummii_v10.ViewModel.RecipeViewModel
import kotlinx.coroutines.delay

@Composable
fun SliderView(recipeViewModel: RecipeViewModel = viewModel(), navController: NavController) {

    val fixedRecipeIds = listOf(637923, 1747693, 1697833)

    LaunchedEffect(fixedRecipeIds) {
        recipeViewModel.fetchFixedRecipes(fixedRecipeIds)
    }

    val fixedRecipes by recipeViewModel.fixedRecipesLiveData.observeAsState(emptyList())
    val limitedRecipes = fixedRecipes.take(3)

    val images = limitedRecipes.map { it.image }
    val recommendedRecipesName = limitedRecipes.map { it.title }
    val recipesUnderText = limitedRecipes.map { it.summary }

    ImageSliderWithIndicator(
        image = images,
        recommendedRecipesName = recommendedRecipesName,
        recipesUnderText = recipesUnderText,
        onRecipeClick = { index ->
            val recipeId = limitedRecipes[index].id
            navController.navigate("RecipeDetail/$recipeId")
        }
    )
}

@Composable
fun ImageSliderItem(imageUrl: String, onClick: () -> Unit) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .height(400.dp)
            .clickable(onClick = onClick),
        contentScale = ContentScale.Crop,
    )
}


@Composable
fun Indicator(active: Boolean) {
    val targetColor = if (active) Color(0xFFFCD4B0) else Color(0xFFA96C36)
    val color by animateColorAsState(targetColor)
    val size = if (active) 5.dp else 5.dp

    Box (modifier = Modifier
        .clip(CircleShape)
        .background(color)
        .size(size)
    )
}

@Composable
fun ImageSliderWithIndicator(image: List<String>, recommendedRecipesName: List<String>, recipesUnderText: List<String>, onRecipeClick: (Int) -> Unit) {
    val currentIndex = remember {mutableStateOf(0)}

    if (image.isNotEmpty()) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(5000)
                currentIndex.value = (currentIndex.value + 1) % image.size
            }
        }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 10.dp)
                ) {
                    ImageSliderItem(imageUrl = image[currentIndex.value], onClick = { onRecipeClick(currentIndex.value) })

                    IndicatorRow(size = image.size, currentIndex = currentIndex.value)

                    Column( modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.BottomStart)
                    ) {
                        Text(
                            text = recommendedRecipesName.getOrNull(currentIndex.value) ?: "Recipe Not Found",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF993300)
                        )
                        Text(
                            text = recipesUnderText.getOrNull(currentIndex.value) ?: "Additional Info Not Found",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF28191B),
                            maxLines = 2,
                        )
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))
        }
    }
    else{

        Text("No images available", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun IndicatorRow(size: Int, currentIndex: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp, top = 16.dp)
            .wrapContentWidth(align = Alignment.End)
    ) {
        repeat(size) { index ->
            Indicator(active = index == currentIndex)
            if (index < size - 1) {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}


