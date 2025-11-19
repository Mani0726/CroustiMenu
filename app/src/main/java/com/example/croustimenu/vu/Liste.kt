package com.example.croustimenu.vu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.croustimenu.MainViewmodel
import com.example.croustimenu.models.Crous
import kotlinx.coroutines.flow.getAndUpdate

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
                longitude = 2.05422F
            )
        )

        viewModel.getAllCrousByAPI()
        val crous by viewModel.crousAPI.collectAsState()

        Text(
            text = "Liste des crous + ${crous.size}"
        )
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
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = " ${crous.nom ?: "N/A"}",
                            style = MaterialTheme.typography.titleMedium
                        )
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
