package com.example.croustimenu.vu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.draw.clip

import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.tileprovider.tilesource.XYTileSource

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
fun CarteScreen() {
    val context = LocalContext.current

    // Initialisation OSMDroid
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

        // ------- TITRE -------
        Text(
            text = "Choisissez votre Crous !",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))


        // ------- MAP OSMDROID -------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    MapView(ctx).apply {
                        setTileSource(LIGHT_MAP_TILE_SOURCE)
                        setMultiTouchControls(true)
                        controller.setZoom(5.0)
                        controller.setCenter(GeoPoint(46.5, 2.5)) // France
                        isTilesScaledToDpi = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewCarte() {
    CarteScreen()
}
