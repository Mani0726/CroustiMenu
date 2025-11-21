package com.example.croustimenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.croustimenu.ui.theme.CroustiMenuTheme
import com.example.croustimenu.vu.CarteScreen
import com.example.croustimenu.vu.FavorisScreen
import com.example.croustimenu.vu.ListeScreen

enum class Screen { CARTE, LISTE, FAVORIS }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Main()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main() {
    CroustiMenuTheme {
        val crousRepository = viewModel<MainViewmodel>()
        var selectedScreen by remember { mutableStateOf(Screen.CARTE) }

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

                            // Icônes Home + Favoris (ACTIVES)
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
                    Row(horizontalArrangement = Arrangement.Center) {
                        IconButton(onClick = { selectedScreen = Screen.CARTE }) {
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

                // ---------------- BOUTONS CARTE / LISTE ----------------
                //pas les boutons si on est le bouton favoris
                if (selectedScreen != Screen.FAVORIS) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
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

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { selectedScreen = Screen.LISTE },
                            colors = ButtonDefaults.buttonColors(
                                containerColor =
                                    if (selectedScreen == Screen.LISTE) Color(0xFFDC6455)
                                    else Color(0xFF365d76)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Liste", color = Color.White)
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
                        Screen.CARTE -> CarteScreen()
                        Screen.LISTE -> ListeScreen(crousRepository)
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
    Main()
}
