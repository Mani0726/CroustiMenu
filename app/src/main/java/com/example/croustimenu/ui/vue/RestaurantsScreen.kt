package com.example.croustimenu.vu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.croustimenu.app.models.entities.Restaurant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantsScreen(
    restaurants: List<Restaurant>,
    regionName: String?,
    onBack: () -> Unit,
    onRestaurantClick: (Restaurant) -> Unit,
    onToggleFavorite: (Restaurant) -> Unit,
    onVoirMenuClick: (Restaurant) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = regionName ?: "Restaurants",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF233D4D),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (restaurants.isEmpty()) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(restaurants) { restaurant ->
                        RestaurantRow(
                            restaurant = restaurant,
                            onClick = { onRestaurantClick(restaurant) },
                            onToggleFavorite = { onToggleFavorite(restaurant) },
                            onVoirMenuClick = { onVoirMenuClick(restaurant) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RestaurantRow(
    restaurant: Restaurant,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onVoirMenuClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F8F8) // fond gris clair, comme Favoris
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = restaurant.nom,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF233D4D) // bleu foncé du projet
                )

                IconButton(onClick = onToggleFavorite) {
                    if (restaurant.estFavori) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Retirer des favoris",
                            tint = Color(0xFFDC6455) // accent rouge/orange
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
                onClick = onVoirMenuClick,
                modifier = Modifier
                    .padding(top = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF233D4D),  // bleu foncé
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Voir le menus")
            }
        }
    }
}
