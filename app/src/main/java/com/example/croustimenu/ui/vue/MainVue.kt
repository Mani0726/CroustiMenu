package com.example.croustimenu.ui.vue

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.croustimenu.MainViewmodel
import com.example.croustimenu.R
import com.example.croustimenu.Screen
import com.example.croustimenu.app.models.entities.Region
import com.example.croustimenu.app.models.entities.Restaurant
import com.example.croustimenu.ui.theme.CroustiMenuTheme
import com.example.croustimenu.vu.CarteScreen
import com.example.croustimenu.vu.FavorisScreen
import com.example.croustimenu.vu.ListeRegionsScreen
import com.example.croustimenu.vu.RestaurantDetailScreen
import com.example.croustimenu.vu.RestaurantsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainVue() {
    CroustiMenuTheme {
        val viewModel: MainViewmodel = viewModel()

        var selectedScreen by remember { mutableStateOf(Screen.REGIONS) }
        var selectedRestaurant by remember { mutableStateOf<Restaurant?>(null) }
        var currentRegionName by remember { mutableStateOf<String?>(null) }

        val restaurants by viewModel.crousAPI.collectAsState()
        val menuDuJour by viewModel.menuDuJour.collectAsState()
        val isMenuLoading by viewModel.isMenuLoading.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF233D4D),
                        titleContentColor = Color.White
                    ),
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo_resto),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(40.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "CroustiMenu",
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFDC6455)
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            selectedScreen = Screen.FAVORIS
                            selectedRestaurant = null
                        }) {
                            Icon(
                                imageVector = Icons.Filled.FavoriteBorder,
                                contentDescription = "Favoris",
                                tint = Color.White,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Onglets Régions / Carte
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            selectedRestaurant = null
                            selectedScreen = Screen.REGIONS
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedScreen == Screen.REGIONS ||
                                selectedScreen == Screen.RESTAURANTS
                            ) Color(0xFFDC6455) else Color(0xFF233D4D),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text("Régions")
                    }

                    Button(
                        onClick = {
                            selectedRestaurant = null
                            selectedScreen = Screen.CARTE
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedScreen == Screen.CARTE)
                                Color(0xFFDC6455) else Color(0xFF233D4D),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text("Carte")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    when (selectedScreen) {
                        Screen.REGIONS -> {
                            ListeRegionsScreen(
                                viewModel = viewModel,
                                onRegionClick = { region: Region ->
                                    currentRegionName = region.libelle
                                    selectedRestaurant = null
                                    viewModel.getRestaurantsByRegion(region.code)
                                    selectedScreen = Screen.RESTAURANTS
                                }
                            )
                        }

                        Screen.RESTAURANTS -> {
                            if (selectedRestaurant == null) {
                                RestaurantsScreen(
                                    restaurants = restaurants,
                                    regionName = currentRegionName,
                                    onBack = {
                                        selectedRestaurant = null
                                        selectedScreen = Screen.REGIONS
                                    },
                                    onRestaurantClick = { restaurant ->
                                        selectedRestaurant = restaurant
                                    },
                                    onToggleFavorite = { restaurant ->
                                        viewModel.toggleFavori(restaurant.code)
                                    }
                                )
                            } else {
                                // Charger le menu du jour pour ce restaurant
                                LaunchedEffect(selectedRestaurant!!.code) {
                                    viewModel.clearMenuDuJour()
                                    viewModel.loadMenuDuJour(selectedRestaurant!!.code)
                                }

                                RestaurantDetailScreen(
                                    restaurant = selectedRestaurant!!,
                                    menu = menuDuJour,
                                    isLoading = isMenuLoading,
                                    onBack = {
                                        selectedRestaurant = null
                                    }
                                )
                            }
                        }

                        Screen.CARTE -> {
                            CarteScreen(
                                onRestaurantSelected = { restaurant ->
                                    selectedRestaurant = restaurant
                                    selectedScreen = Screen.RESTAURANTS
                                }
                            )
                        }

                        Screen.FAVORIS -> {
                            FavorisScreen(
                                viewModel = viewModel,
                                onRestaurantClick = { restaurant ->
                                    selectedRestaurant = restaurant
                                    selectedScreen = Screen.RESTAURANTS
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMain() {
    MainVue()
}
