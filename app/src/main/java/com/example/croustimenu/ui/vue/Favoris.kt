package com.example.croustimenu.vu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.croustimenu.MainViewmodel
import com.example.croustimenu.app.models.entities.Restaurant

@Composable
fun FavorisScreen(
    viewModel: MainViewmodel,
    onRestaurantClick: (Restaurant) -> Unit
) {
    val restaurants by viewModel.crousAPI.collectAsState()
    val favoris    by viewModel.crousFavorisAPI.collectAsState()
    val isLoading  by viewModel.isFavorisLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFavorisScreenData()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator()
            }

            favoris.isEmpty() -> {
                Text(
                    text = "Vous n'avez pas encore de restaurant en favori.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(favoris) { restaurant ->
                        FavoriCard(
                            restaurant = restaurant,
                            onToggleFavorite = {
                                viewModel.toggleFavori(restaurant.code)
                            },
                            onRestaurantClick = { onRestaurantClick(restaurant) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoriCard(
    restaurant: Restaurant,
    onToggleFavorite: () -> Unit,
    onRestaurantClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F8F8)
        ),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = restaurant.nom,
                    fontSize = 18.sp,
                    color = Color(0xFF233D4D),
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = onToggleFavorite) {
                    if (restaurant.estFavori) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Retirer des favoris",
                            tint = Color(0xFFDC6455)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Ajouter aux favoris",
                            tint = Color(0xFFDC6455)
                        )
                    }
                }
            }

            restaurant.adresse?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Button(
                onClick = onRestaurantClick,
                modifier = Modifier
                    .padding(top = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF233D4D),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Voir le menu")
            }
        }
    }
}
