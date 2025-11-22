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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
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
import com.example.croustimenu.ui.theme.CroustiMenuTheme
import com.example.croustimenu.vu.CarteScreen
import com.example.croustimenu.vu.FavorisScreen
import com.example.croustimenu.vu.ListeRegionsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainVue() {
    CroustiMenuTheme {
        val crousRepository = viewModel<MainViewmodel>()
        var selectedScreen by remember { mutableStateOf(Screen.REGIONS) }

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

                            // Logo + Titre
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

                            // Icônes Favoris
                            Row {
                                IconButton(onClick = { selectedScreen = Screen.FAVORIS }) {
                                    Icon(
                                        imageVector = androidx.compose.material.icons.Icons.Filled.FavoriteBorder,
                                        contentDescription = "Favoris",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    },
                    navigationIcon = {}
                )
            },

            bottomBar = {
                BottomAppBar(
                    containerColor = Color(0xFF233D4D),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = { selectedScreen = Screen.REGIONS }) {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Filled.Home,
                                contentDescription = "Home",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {

                // ---------------- BOUTONS REGIONS / CARTE ----------------
                // Afficher les boutons uniquement quand on a sélectionné une région (= on est sur LISTE ou CARTE)
                if (selectedScreen == Screen.REGIONS || selectedScreen == Screen.CARTE) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { selectedScreen = Screen.REGIONS },
                            colors = ButtonDefaults.buttonColors(
                                containerColor =
                                    if (selectedScreen == Screen.REGIONS) Color(0xFFDC6455)
                                    else Color(0xFF365d76)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Liste", color = Color.White)
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { selectedScreen = Screen.CARTE },
                            colors = ButtonDefaults.buttonColors(
                                containerColor =
                                    if (selectedScreen == Screen.CARTE) Color(0xFFDC6455)
                                    else Color(0xFF365d76)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Carte", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // ---------------- CONTENU ----------------
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    when (selectedScreen) {
                        Screen.REGIONS -> ListeRegionsScreen(
                            viewModel = crousRepository,
                            onRegionClick = { codeRegion ->
                                // Charger les restaurants de cette région
                                crousRepository.getRestaurantsByRegion(codeRegion)
                                // Aller à l'écran liste
                                selectedScreen = Screen.REGIONS  // ⬅️ CORRIGÉ ICI
                            }
                        )
                        Screen.CARTE -> CarteScreen()
                        Screen.FAVORIS -> FavorisScreen(crousRepository)
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