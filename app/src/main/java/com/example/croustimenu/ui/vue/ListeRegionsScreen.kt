package com.example.croustimenu.vu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import com.example.croustimenu.MainViewmodel
import com.example.croustimenu.app.models.entities.Region

@Composable
fun ListeRegionsScreen(
    viewModel: MainViewmodel,
    onRegionClick: (Region) -> Unit
) {
    val regions by viewModel.regionsAPI.collectAsState()

    LaunchedEffect(Unit) {
        if (regions.isEmpty()) {
            viewModel.getAllRegions()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (regions.isEmpty()) {
            CircularProgressIndicator()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(regions) { region ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { onRegionClick(region) },
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF233D4D)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = region.libelle,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                modifier = Modifier
                                    .weight(1f)
                            )

                            Icon(
                                imageVector = androidx.compose.material.icons.Icons
                                    .AutoMirrored
                                    .Filled
                                    .ArrowForward,
                                contentDescription = "Voir les restaurants",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
