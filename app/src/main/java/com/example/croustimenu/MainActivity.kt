package com.example.croustimenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.croustimenu.ui.vue.MainVue

enum class Screen {REGIONS, RESTAURANTS, CARTE, FAVORIS }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainVue()
        }
    }
}
