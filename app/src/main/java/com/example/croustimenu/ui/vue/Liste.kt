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
import androidx.compose.material.icons.filled.FavoriteBorder
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
import com.example.croustimenu.MainViewmodel
import com.example.croustimenu.models.Crous

@Composable
fun ListeScreen(viewModel: MainViewmodel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        viewModel.addCrous(
            Crous(
                id = 1,
                nom = "Crous Castres",
                latitude = 5.000207F,
                longitude = 2.05422F,
                adresse = "Castre",
                estFavori = false
            )
        )

        viewModel.getAllCrousByAPI()
        val crous by viewModel.crousAPI.collectAsState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(crous) { crous ->
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
                                viewModel.toggleFavori(crous.code ?: 0)  // ⬅️ Utilisez "code"
                            }) {
                                Icon(
                                    imageVector = if (crous.estFavori)
                                        androidx.compose.material.icons.Icons.Filled.Favorite
                                    else
                                        androidx.compose.material.icons.Icons.Filled.FavoriteBorder,
                                    contentDescription = "favoris",
                                    tint = if (crous.estFavori) Color(0xFFFF9800) else Color.White
                                )
                            }
                        }

                        // adresse
                        Text(
                            text = crous.adresse ?: "N/A",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFFFF9800),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        // --- Ligne 3 : Bouton Voir le menu ---
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



//@Preview(showBackground = true)
//@Composable
//fun PreviewListe() {
//    ListeScreen()
//}
