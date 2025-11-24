package com.example.croustimenu.vu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.croustimenu.app.models.entities.CategoriePlat
import com.example.croustimenu.app.models.entities.MenuDuJour
import com.example.croustimenu.app.models.entities.Plat
import com.example.croustimenu.app.models.entities.Repas
import com.example.croustimenu.app.models.entities.Restaurant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailScreen(
    restaurant: Restaurant,
    menu: MenuDuJour?,
    isLoading: Boolean,
    onBack: () -> Unit,
    onToggleFavorite: (Restaurant) -> Unit
) {
    Scaffold(
        topBar = {
            // Bandeau arrondi pour le titre du restaurant
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF233D4D)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Flèche retour + nom du resto
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            IconButton(onClick = onBack) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Retour",
                                    tint = Color.White
                                )
                            }

                            Text(
                                text = restaurant.nom,
                                color = Color.White,
                                fontSize = 18.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        // Icône favori
                        IconButton(onClick = { onToggleFavorite(restaurant) }) {
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

                    // Date / sous-titre
                    Text(
                        text = menu?.date ?: "Détail et menu du jour",
                        color = Color.White,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(start = 52.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Bloc détail restaurant
            item {
                DetailRestaurantCard(restaurant)
            }

            //Bloc menu du jour
            when {
                isLoading -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                menu == null -> {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF8F8F8)
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Menu du jour",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF233D4D)
                                )
                                Spacer(modifier = Modifier.padding(top = 8.dp))
                                Text(
                                    text = "Aucun menu disponible pour aujourd'hui.",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }

                else -> {
                    items(menu.liste_repas) { repas ->
                        RepasCard(repas)
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRestaurantCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F8F8)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = restaurant.nom,
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF233D4D)
            )

            restaurant.adresse?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            restaurant.telephone?.let {
                Text(
                    text = "Téléphone : $it",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            restaurant.email?.let {
                Text(
                    text = "Email : $it",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun RepasCard(repas: Repas) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F8F8)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = repas.type,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF233D4D)
            )

            repas.liste_categories_plat.forEach { categorie ->
                CategorieSection(categorie)
            }
        }
    }
}

@Composable
private fun CategorieSection(categorie: CategoriePlat) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Text(
            text = categorie.libelle,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFDC6455)
        )
        categorie.liste_plats.forEach { plat ->
            PlatRow(plat)
        }
    }
}

@Composable
private fun PlatRow(plat: Plat) {
    Text(
        text = "• ${plat.libelle}",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(top = 4.dp)
    )
}
