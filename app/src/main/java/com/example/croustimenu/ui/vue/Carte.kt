package com.example.croustimenu.vu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.draw.clip

import com.example.croustimenu.MainViewmodel
import com.example.croustimenu.R

import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.views.overlay.Marker
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.croustimenu.app.models.entities.Restaurant

// ---- SOURCE DE CARTE CLAIRE ----
val LIGHT_MAP_TILE_SOURCE = XYTileSource(
    "CartoLightMatter",
    0, 19, 256, ".png",
    arrayOf(
        "https://a.basemaps.cartocdn.com/light_all/",
        "https://b.basemaps.cartocdn.com/light_all/",
        "https://c.basemaps.cartocdn.com/light_all/",
        "https://d.basemaps.cartocdn.com/light_all/"
    ),
    "© OpenStreetMap contributors © CARTO"
)

@Composable
fun CarteScreen(
    viewModel: MainViewmodel = viewModel(),
    onRestaurantSelected: (Restaurant) -> Unit = {}
) {
    val context = LocalContext.current
    val restaurants by viewModel.crousAPI.collectAsState()

    LaunchedEffect(Unit) {
        if (restaurants.isEmpty()) {
            viewModel.getAllCrousByAPI()
        }
    }

    DisposableEffect(Unit) {
        Configuration.getInstance().apply {
            userAgentValue = context.packageName
            osmdroidBasePath = context.filesDir
            osmdroidTileCache = context.cacheDir
        }
        onDispose {}
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Choisissez votre Crous !",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .height(500.dp)
        ) {

            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    MapView(ctx).apply {
                        setTileSource(LIGHT_MAP_TILE_SOURCE)
                        setMultiTouchControls(true)
                        controller.setZoom(5.5)
                        controller.setCenter(GeoPoint(46.5, 2.5))
                        isTilesScaledToDpi = true
                    }
                },
                update = { mapView ->
                    mapView.overlays.clear()

                    restaurants.forEach { resto ->
                        val lat = resto.latitude.toDouble()
                        val lon = resto.longitude.toDouble()

                        val marker = Marker(mapView).apply {
                            position = GeoPoint(lat, lon)
                            title = resto.nom
                            subDescription = resto.adresse ?: ""

                            icon = context.getDrawable(R.drawable.pointer)
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                            // --- OUVERTURE DU DETAIL + MENU ---
                            setOnMarkerClickListener { _, _ ->
                                onRestaurantSelected(resto)
                                true
                            }
                        }

                        mapView.overlays.add(marker)
                    }

                    mapView.invalidate()
                }

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCarte() {
    CarteScreen()
}
