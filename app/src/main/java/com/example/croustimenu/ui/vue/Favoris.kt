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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.croustimenu.MainViewmodel

@Composable
fun FavorisScreen(viewModel: MainViewmodel) {
    val crousFavoris by viewModel.crousFavorisAPI.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (crousFavoris.isEmpty()) {
            // Message si aucun favori
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Aucun favori pour le moment",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
        } else {
            // Liste des favoris
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(crousFavoris) { crous ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            // --- Ligne 1 : Nom + cœur ---
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = crous.nom ?: "N/A",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.weight(1f)
                                )

                                IconButton(onClick = {
                                    viewModel.toggleFavori(crous.code ?: 0)
                                }) {
                                    Icon(
                                        imageVector = androidx.compose.material.icons.Icons.Filled.Favorite,
                                        contentDescription = "Retirer des favoris",
                                        tint = Color(0xFFFF9800)
                                    )
                                }
                            }

                            // Adresse
                            Text(
                                text = crous.adresse ?: "N/A",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFFFF9800),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )

                            // --- Bouton Voir le menu ---
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFDC6455)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = "Voir le menu",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}